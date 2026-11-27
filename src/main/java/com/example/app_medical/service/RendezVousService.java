package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;
    private final CreneauRepository creneauRepository;
    private final RendezVousMapper mapper;



    public RendezVous confirmer(Long id) {

        RendezVous rdv =
                rendezVousRepository.findById(id).orElseThrow();

        if(rdv!=null) {
            rdv.getCreneau().setDisponible(false);

            rdv.setConfirme(true);
            return rendezVousRepository.save(rdv);
        }
        return null;
    }

    public RendezVous annuler(Long id) {

        RendezVous rdv =
                rendezVousRepository.findById(id).orElseThrow();
        if(rdv!=null) {
            rdv.getCreneau().setDisponible(true);

            rdv.setConfirme(false);
            rdv.setRefuse(true);
            return rendezVousRepository.save(rdv);
        }
        return null;


    }
    @Transactional
    public RendezVousResponseDTO create(RendezVousRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Creneau creneau = creneauRepository.findById(dto.getCreneauId())
                .orElseThrow(() -> new RuntimeException("Creneau not found"));

        // 1. Vérifier si le créneau est disponible
        if (creneau.getDisponible() != null && !creneau.getDisponible()) {
            throw new RuntimeException("Ce créneau est déjà réservé");
        }

        // 2. Vérifier si la spécialité du médecin lié au créneau correspond à celle recommandée par le chatbot
        String specialiteCreneau = (creneau.getPlageHoraire() != null
                && creneau.getPlageHoraire().getMedecin() != null
                && creneau.getPlageHoraire().getMedecin().getSpecialite() != null)
                ? creneau.getPlageHoraire().getMedecin().getSpecialite().getNom()
                : null;

        String resumeValide = null;
        if (dto.getResumeChatbot() != null
                && !dto.getResumeChatbot().isBlank()
                && dto.getSpecialiteRecommandee() != null
                && specialiteCreneau != null
                && specialiteCreneau.equalsIgnoreCase(dto.getSpecialiteRecommandee().trim())) {
            resumeValide = dto.getResumeChatbot().trim();
        }

        // 3. Créer le rendez-vous
        RendezVous rendezVous = new RendezVous();
        rendezVous.setDateReservation(LocalDate.now());
        rendezVous.setConfirme(false);
        rendezVous.setPatient(patient);
        rendezVous.setCreneau(creneau);
        rendezVous.setResumeChatbot(resumeValide);

        // 4. Rendre le créneau indisponible et le sauvegarder
        creneau.setDisponible(false);
        creneauRepository.save(creneau);

        // 5. Sauvegarder le rendez-vous et retourner le DTO
        RendezVous saved = rendezVousRepository.save(rendezVous);
        return mapper.toDTO(saved);
    }

    public List<RendezVousResponseDTO> getAll() {

        return rendezVousRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public RendezVous getById(Long id) {

        RendezVous rendezVous =
                rendezVousRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Rendez-vous not found"));

        return rendezVous;
    }

    public RendezVousResponseDTO update(
            Long id,
            RendezVousRequestDTO dto) {

        RendezVous rendezVous =
                rendezVousRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Rendez-vous not found"));

        Patient patient =
                patientRepository.findById(dto.getPatientId())
                        .orElseThrow(() ->
                                new RuntimeException("Patient not found"));

        Creneau creneau =
                creneauRepository.findById(dto.getCreneauId())
                        .orElseThrow(() ->
                                new RuntimeException("Creneau not found"));

        rendezVous.setPatient(patient);
        rendezVous.setCreneau(creneau);

        return mapper.toDTO(
                rendezVousRepository.save(rendezVous)
        );
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<RendezVous> rd = rendezVousRepository.findById(id);
        if (rd.isPresent()) {
            RendezVous rdv = rd.get();

            Creneau creneau = rdv.getCreneau();
            if (creneau != null) {
                creneau.setDisponible(true);
                creneauRepository.save(creneau);
            }

            rendezVousRepository.delete(rdv);
            return true;
        }
        return false;
    }
    public List<RendezVousResponseDTO> getRendezVousParPatientEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));

        List<RendezVous> rendezVousList = rendezVousRepository.findByPatientId(patient.getId());

        return rendezVousList.stream()
                .map(mapper::toDTO)
                .toList();
    }
}