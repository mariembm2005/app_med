package com.example.app_medical.service;

import com.example.app_medical.dto.ChatMessageDTO;
import com.example.app_medical.dto.ChatResponseDTO;
import com.example.app_medical.entity.Specialite;
import com.example.app_medical.repository.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    private static final String URL = "https://api.groq.com/openai/v1/chat/completions";

    private final SpecialiteRepository specialiteRepo;
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate;

    @Value("${groq.api-key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    public ChatbotService(SpecialiteRepository specialiteRepo) {
        this.specialiteRepo = specialiteRepo;
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        f.setConnectTimeout(5000);
        f.setReadTimeout(20000);
        this.restTemplate = new RestTemplate(f);
    }

    public ChatResponseDTO repondre(List<ChatMessageDTO> historique) {
        List<String> noms = specialiteRepo.findAll().stream()
                .map(Specialite::getNom)
                .toList();
        try {
            String json = appelerGroq(historique, noms);
            ChatResponseDTO r = mapper.readValue(json, ChatResponseDTO.class);

            List<String> valides;
            if (r.specialites() == null) {
                valides = List.of();
            } else {
                valides = r.specialites().stream().filter(noms::contains).toList();
            }

            List<String> rapides;
            if (r.reponsesRapides() == null) {
                rapides = List.of();
            } else {
                rapides = r.reponsesRapides();
            }

            boolean estTermine = r.termine() && !valides.isEmpty();

            String resume = null;
            if (estTermine && r.resume() != null && !r.resume().isBlank()) {
                resume = r.resume().trim();
            }

            return new ChatResponseDTO(r.reponse(), rapides, valides, estTermine, resume);
        } catch (Exception e) {
            return secours(historique, noms);
        }
    }

    private String appelerGroq(List<ChatMessageDTO> historique, List<String> noms) {
        List messages = new ArrayList();
        messages.add(Map.of("role", "system", "content", prompt(noms)));

        for (ChatMessageDTO m : historique) {
            String role = "user".equals(m.role()) ? "user" : "assistant";
            messages.add(Map.of("role", role, "content", m.text()));
        }

        Map body = new HashMap();
        body.put("model", model);
        body.put("messages", messages);
        body.put("response_format", Map.of("type", "json_object"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity request = new HttpEntity(body, headers);
        JsonNode res = restTemplate.postForObject(URL, request, JsonNode.class);
        if (res == null) {
            throw new RuntimeException("Réponse Groq vide");
        }
        return res.at("/choices/0/message/content").asString();
    }

    private String prompt(List<String> noms) {
        return """
            Tu es un assistant d'orientation pour une application de rendez-vous médicaux.
            Ton rôle n'est PAS de diagnostiquer ni de soigner : tu aides seulement le patient
            à choisir quelle spécialité de médecin consulter. Ne donne jamais de médicament.
            Réponds en français, avec des phrases courtes et simples.
            Tant que tu manques d'informations, pose UNE question courte.
            Quand tu as assez d'informations, propose une ou deux spécialités,
            uniquement parmi cette liste : %s.
            Si les symptômes semblent graves (douleur à la poitrine, difficulté à respirer,
            perte de connaissance), dis au patient d'appeler le 190.
            Réponds UNIQUEMENT avec un objet JSON de cette forme :
            {"reponse":"...","reponsesRapides":["..."],"specialites":["..."],"termine":false,"resume":""}
            "reponsesRapides" contient 0 à 3 réponses courtes possibles du patient.
            "specialites" est vide tant que tu poses des questions.
            "termine" vaut true seulement quand tu proposes les spécialités.
            "resume" est vide "" tant que "termine" vaut false.
            Quand "termine" vaut true :
            - ajoute dans "reponse" : Ceci n'est pas un diagnostic.
            - remplis "resume" avec un résumé court et clair des symptômes du patient et de la spécialité conseillée pour le médecin.
            """.formatted(String.join(", ", noms));
    }

    private ChatResponseDTO secours(List<ChatMessageDTO> historique, List<String> noms) {
        String texte = historique.stream()
                .filter(m -> "user".equals(m.role()))
                .map(ChatMessageDTO::text)
                .collect(Collectors.joining(" "))
                .toLowerCase();

        String spec;
        if (texte.contains("coeur") || texte.contains("poitrine")) {
            spec = "Cardiologie";
        } else if (texte.contains("peau") || texte.contains("bouton")) {
            spec = "Dermatologie";
        } else {
            spec = "Médecine générale";
        }

        List<String> specs = noms.contains(spec) ? List.of(spec) : List.of();
        boolean estTermine = !specs.isEmpty();

        String resume = null;
        if (estTermine) {
            resume = "Symptômes signalés : " + texte + ". Spécialité recommandée : " + spec + ".";
        }

        return new ChatResponseDTO(
                "Je vous oriente vers : " + spec + ". Ceci n'est pas un diagnostic.",
                List.of(),
                specs,
                estTermine,
                resume
        );
    }
}