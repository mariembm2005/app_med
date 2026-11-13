package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.PathVariable;

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

        if (dto.getHeureDebut() == null ||
                dto.getHeureFin() == null) {
            throw new IllegalArgumentException(
                    "Les heures de début et de fin sont obligatoires."
            );
        }

        if (!dto.getHeureFin().isAfter(dto.getHeureDebut())) {
            throw new IllegalArgumentException(
                    "L'heure de fin doit être après l'heure de début."
            );
        }

        if (dto.getDureeCreneau() == null ||
                dto.getDureeCreneau() <= 0) {
            throw new IllegalArgumentException(
                    "La durée du créneau doit être supérieure à zéro."
            );
        }
    }
    @Transactional
    public PlageHoraire create(PlageHoraireRequestDTO dto) {

        validerDto(dto);

        Medecin medecin = medecinRepository
                .findById(dto.getMedecinId())
                .orElseThrow(() -> new RuntimeException(
                        "Médecin introuvable avec l'id : "
                                + dto.getMedecinId()
                ));

        List<PlageHoraire> plagesExistantes =
                repository.findByMedecinIdAndDate(
                        dto.getMedecinId(),
                        dto.getDate()
                );

        for (PlageHoraire plageExistante : plagesExistantes) {
            boolean chevauchement =
                    dto.getHeureDebut()
                            .isBefore(plageExistante.getHeureFin())
                            &&
                            dto.getHeureFin()
                                    .isAfter(plageExistante.getHeureDebut());

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

        PlageHoraire plage = new PlageHoraire();

        plage.setDate(dto.getDate());
        plage.setHeureDebut(dto.getHeureDebut());
        plage.setHeureFin(dto.getHeureFin());
        plage.setMedecin(medecin);

        List<Creneau> creneaux =
                plage.generateCreneaux(dto.getDureeCreneau());

        plage.setCreneaux(creneaux);

        return repository.save(plage);
    }

    public List<PlageHoraire> getAll() {

        return repository.findAll()
                .stream()
                .toList();
    }
    @Transactional
    public PlageHoraire update(
            Long id,
            PlageHoraireRequestDTO dto
    ) {
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
        plage.getCreneaux().addAll(
                plage.generateCreneaux(dto.getDureeCreneau())
        );

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
        if (po.isPresent()) {
            return po.get();
        }
        return null;
    }
}