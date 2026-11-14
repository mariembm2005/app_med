package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.*;

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

    private void validerDto(PlageHoraireRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException(
                    "Les informations de la plage sont obligatoires."
            );
        }

        if (dto.getMedecinId() == null) {
            throw new IllegalArgumentException(
                    "L'identifiant du médecin est obligatoire."
            );
        }

        if (dto.getDate() == null) {
            throw new IllegalArgumentException(
                    "La date est obligatoire."
            );
        }

        if (dto.getHeureDebut() == null || dto.getHeureFin() == null) {
            throw new IllegalArgumentException(
                    "Les heures de début et de fin sont obligatoires."
            );
        }

        if (!dto.getHeureFin().isAfter(dto.getHeureDebut())) {
            throw new IllegalArgumentException(
                    "L'heure de fin doit être après l'heure de début."
            );
        }

        if (dto.getDureeCreneau() == null || dto.getDureeCreneau() <= 0) {
            throw new IllegalArgumentException(
                    "La durée du créneau doit être supérieure à zéro."
            );
        }
    }

    @Transactional
    public PlageHoraire create(PlageHoraireRequestDTO request) {
        validerDto(request);

        // Vérification des chevauchements de plages horaires pour ce médecin à cette date
        List<PlageHoraire> plagesExistantes =
                repository.findByMedecinIdAndDate(
                        request.getMedecinId(),
                        request.getDate()
                );

        for (PlageHoraire plageExistante : plagesExistantes) {
            boolean chevauchement =
                    request.getHeureDebut().isBefore(plageExistante.getHeureFin())
                            && request.getHeureFin().isAfter(plageExistante.getHeureDebut());

            if (chevauchement) {
                throw new IllegalStateException(
                        "Création impossible : une plage existe déjà entre "
                                + plageExistante.getHeureDebut()
                                + " et "
                                + plageExistante.getHeureFin()
                                + "."
                );
            }
        }

        // 1. Créer l'entité via le mapper
        PlageHoraire plage = mapper.toEntity(request);

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

        // 2. Génération automatique des créneaux
        int dureeCreneaux = request.getDureeCreneau() != null ? request.getDureeCreneau() : 30;

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

    @Transactional
    public PlageHoraire update(Long id, PlageHoraireRequestDTO dto) {
        PlageHoraire plage = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Plage horaire introuvable avec l'id : " + id
                ));

        for (Creneau creneau : plage.getCreneaux()) {
            if (Boolean.FALSE.equals(creneau.getDisponible())) {
                throw new IllegalStateException(
                        "Modification impossible : le créneau "
                                + creneau.getHeureDebut()
                                + " - "
                                + creneau.getHeureFin()
                                + " est déjà réservé."
                );
            }
        }

        plage.setDate(dto.getDate());
        plage.setHeureDebut(dto.getHeureDebut());
        plage.setHeureFin(dto.getHeureFin());

        plage.getCreneaux().clear();

        int dureeCreneaux = dto.getDureeCreneau() != null ? dto.getDureeCreneau() : 30;
        LocalTime debut = plage.getHeureDebut();
        LocalTime fin = plage.getHeureFin();
        LocalTime courant = debut;

        while (!courant.plusMinutes(dureeCreneaux).isAfter(fin)) {
            Creneau creneau = new Creneau();
            creneau.setHeureDebut(courant);
            LocalTime prochainCourant = courant.plusMinutes(dureeCreneaux);
            creneau.setHeureFin(prochainCourant);
            creneau.setDisponible(true);
            creneau.setPlageHoraire(plage);
            plage.getCreneaux().add(creneau);
            courant = prochainCourant;
        }

        return repository.save(plage);
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
        return po.orElse(null);
    }

    public List<PlageHoraire> getByMedecinId(Long medecinId) {
        return repository.findAll().stream()
                .filter(p -> p.getMedecin() != null && p.getMedecin().getId().equals(medecinId))
                .toList();
    }
}