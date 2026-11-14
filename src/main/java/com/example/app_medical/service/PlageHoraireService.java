package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlageHoraireService {

    private final PlageHoraireRepository repository;
    private final CreneauRepository creneauRepository;
    private final RendezVousRepository rendezVousRepository;
    private final MedecinRepository medecinRepository;
    private final PlageHoraireMapper mapper;

    @Transactional
    public PlageHoraire create(PlageHoraireRequestDTO request) {
        // 1. Créer l'entité via le mapper ou manuellement si le mapper coince avec les LocalTime
        PlageHoraire plage = mapper.toEntity(request);

        // Sécurité au cas où le mapper ne convertit pas les heures ou la date automatiquement :
        // Sécurité si les heures viennent déjà en LocalTime :
        if (request.getHeureDebut() != null && plage.getHeureDebut() == null) {
            plage.setHeureDebut(request.getHeureDebut());
        }
        if (request.getHeureFin() != null && plage.getHeureFin() == null) {
            plage.setHeureFin(request.getHeureFin());
        }

        plage.setMedecin(
                medecinRepository.findById(request.getMedecinId())
                        .orElseThrow(() -> new RuntimeException("Médecin introuvable"))
        );

        // Sauvegarde d'abord la plage pour obtenir son ID
        PlageHoraire savedPlage = repository.save(plage);

        // 2. Génération automatique des créneaux (par pas de 30 minutes)
        int dureeCreneaux = 30;

        LocalTime debut = savedPlage.getHeureDebut();
        LocalTime fin = savedPlage.getHeureFin();

        List<Creneau> creneaux = new ArrayList<>();
        LocalTime courant = debut;

        while (!courant.plusMinutes(dureeCreneaux).isAfter(fin)) {
            Creneau creneau = new Creneau();
            creneau.setHeureDebut(courant);

            LocalTime prochainCourant = courant.plusMinutes(dureeCreneaux);
            creneau.setHeureFin(prochainCourant);

            creneau.setDisponible(true);
            creneau.setPlageHoraire(savedPlage); // Liaison avec la plage

            creneaux.add(creneau);
            courant = prochainCourant;
        }

        // Sauvegarder tous les créneaux générés en base
        creneauRepository.saveAll(creneaux);
        savedPlage.setCreneaux(creneaux);

        return savedPlage;
    }

    public List<PlageHoraire> getAll() {

        return repository.findAll()
                .stream()
                .toList();
    }
    public PlageHoraire update(PlageHoraireRequestDTO request, Long id) {
        Optional<PlageHoraire> plage = repository.findById(id);
        if (plage.isPresent()) {
            PlageHoraire po = plage.get();
            po.setId(id);
            po.setDate(request.getDate());
            po.setMedecin(medecinRepository.getById(request.getMedecinId()));
            po.setHeureDebut(request.getHeureDebut());
            po.setHeureFin(request.getHeureFin());
            repository.save(po);
            return po;
        }
        return null;
    }
    @Transactional
    public void delete(Long id) {

        PlageHoraire plage = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Plage horaire introuvable avec l'id : " + id
                ));

        List<Creneau> creneaux = new ArrayList<>(plage.getCreneaux());

        for (Creneau creneau : creneaux) {

            List<RendezVous> rendezVousList =
                    rendezVousRepository.findAll()
                            .stream()
                            .filter(rendezVous ->
                                    rendezVous.getCreneau() != null
                                            && rendezVous.getCreneau()
                                            .getId()
                                            .equals(creneau.getId())
                            )
                            .toList();

            for (RendezVous rendezVous : rendezVousList) {
                rendezVous.setCreneau(null);
                rendezVousRepository.save(rendezVous);
            }

            plage.getCreneaux().remove(creneau);
            creneauRepository.delete(creneau);
        }

        repository.delete(plage);
    }

    public PlageHoraire findById(Long id) {
        Optional<PlageHoraire> po = repository.findById(id);
        if (po.isPresent()) {
            return po.get();
        }
        return null;
    }
}