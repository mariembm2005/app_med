package com.example.app_medical.mapper;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.repository.PlageHoraireRepository;
import com.example.app_medical.repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreneauMapper {

    private final PlageHoraireRepository plageHoraireRepository;
    private final RendezVousRepository rendezVousRepository;

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

        // Récupérer les informations du patient si le créneau est associé à un rendez-vous
        RendezVous rdv = rendezVousRepository
                .findByCreneauId(creneau.getId())
                .orElse(null);

        if (rdv != null && rdv.getPatient() != null) {
            Patient patient = rdv.getPatient();
            dto.setPatientNom(patient.getNom());
            dto.setPatientPrenom(patient.getPrenom());
            dto.setPatientTelephone(patient.getTelephone());
        }

        return dto;
    }

    // Gardé aussi au cas où votre code appelle toDTO
    public CreneauResponseDTO toDTO(Creneau creneau) {
        return toResponse(creneau);
    }

    public Creneau toEntity(CreneauRequestDTO request) {
        Creneau creneau = new Creneau();
        creneau.setId(request.getId());
        creneau.setDisponible(true); // Par défaut un nouveau créneau est disponible
        creneau.setHeureFin(request.getHeureFin());
        creneau.setHeureDebut(request.getHeureDebut());
        creneau.setTypeSoin(request.getTypeSoin());

        if (request.getPlageHoraireId() != null) {
            creneau.setPlageHoraire(plageHoraireRepository.getReferenceById(request.getPlageHoraireId()));
        }

        return creneau;
    }
}