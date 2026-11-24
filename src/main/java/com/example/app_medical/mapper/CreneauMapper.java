package com.example.app_medical.mapper;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.repository.CreneauRepository;
import com.example.app_medical.repository.PlageHoraireRepository;
import com.example.app_medical.repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreneauMapper {

    private final PlageHoraireRepository plageHoraireRepository;
    private final RendezVousRepository rendezVousRepository;
    private final CreneauRepository creneauRepository;

    public CreneauResponseDTO toResponse(Creneau creneau) {
        CreneauResponseDTO dto = new CreneauResponseDTO();
        dto.setId(creneau.getId());
        dto.setHeureDebut(creneau.getHeureDebut());
        dto.setHeureFin(creneau.getHeureFin());
        dto.setDisponible(creneau.getDisponible());
        dto.setTypeSoin(creneau.getTypeSoin());

        if (creneau.getPlageHoraire() != null) {
            PlageHoraire plage = creneau.getPlageHoraire();
            dto.setDate(plage.getDate());
            dto.setPlageId(plage.getId());

            if (plage.getMedecin() != null) {
                dto.setMedecinId(plage.getMedecin().getId());
                dto.setMedecinNom(plage.getMedecin().getPrenom() + " " + plage.getMedecin().getNom());
            }
        }
        rendezVousRepository.findByCreneauId(creneau.getId())
                .map(RendezVous::getPatient)
                .ifPresent(patient -> {
                    dto.setPatientNom(patient.getNom());
                    dto.setPatientPrenom(patient.getPrenom());
                    dto.setPatientTelephone(patient.getTelephone());
                });

        return dto;
    }

    public Creneau toEntity(CreneauRequestDTO request) {
        Creneau creneau = new Creneau();
        creneau.setId(request.getId());
        creneau.setDisponible(true);
        creneau.setHeureFin(request.getHeureFin());
        creneau.setHeureDebut(request.getHeureDebut());
        creneau.setTypeSoin(request.getTypeSoin());

        if (request.getPlageHoraireId() != null) {
            creneau.setPlageHoraire(plageHoraireRepository.getReferenceById(request.getPlageHoraireId()));
        }

        return creneau;
    }
}