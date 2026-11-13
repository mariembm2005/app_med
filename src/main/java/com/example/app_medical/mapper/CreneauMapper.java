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
    public CreneauResponseDTO toDTO(Creneau creneau) {

        CreneauResponseDTO dto = new CreneauResponseDTO();

        dto.setId(creneau.getId());
        dto.setHeureDebut(creneau.getHeureDebut());
        dto.setHeureFin(creneau.getHeureFin());
        dto.setDisponible(creneau.getDisponible());
        dto.setTypeSoin(creneau.getTypeSoin());
        PlageHoraire plage = creneau.getPlageHoraire();
        dto.setDate(plage.getDate());
        dto.setPlageId(plage.getId());
        dto.setMedecinId(plage.getMedecin().getId());
        dto.setMedecinNom(plage.getMedecin().getPrenom() + " " + plage.getMedecin().getNom());
        RendezVous rdv = rendezVousRepository
                .findByCreneauId(creneau.getId())
                .orElse(null);

        if (rdv != null) {

            Patient patient = rdv.getPatient();

            if (patient != null) {
                dto.setPatientNom(patient.getNom());
                dto.setPatientPrenom(patient.getPrenom());
                dto.setPatientTelephone(patient.getTelephone());
            }
        }

        return dto;
    }

    public Creneau toEntity(CreneauRequestDTO request){
        Creneau creneau = new Creneau();
        creneau.setId(request.getId());

        creneau.setDisponible(creneau.getDisponible());
        creneau.setHeureFin(request.getHeureFin());
        creneau.setHeureDebut(request.getHeureDebut());
        creneau.setTypeSoin(request.getTypeSoin());
        creneau.setPlageHoraire(plageHoraireRepository.getReferenceById(request.getPlageHoraireId()));
        return creneau;

    }
}
