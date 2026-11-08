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
    public CreneauResponseDTO toResponse(Creneau creneau){
        CreneauResponseDTO creneauResponseDTO = new CreneauResponseDTO();
        creneauResponseDTO.setId(creneau.getId());
        creneauResponseDTO.setDisponible(creneau.isDisponible());
        creneauResponseDTO.setHeureFin(creneau.getHeureFin());
        creneauResponseDTO.setHeureDebut(creneau.getHeureDebut());
        creneauResponseDTO.setTypeSoin(creneau.getTypeSoin());
        return creneauResponseDTO;

    }

    public Creneau toEntity(CreneauRequestDTO request){
        Creneau creneau = new Creneau();
        creneau.setId(request.getId());
        creneau.setDisponible(creneau.isDisponible());
        creneau.setHeureFin(request.getHeureFin());
        creneau.setHeureDebut(request.getHeureDebut());
        creneau.setTypeSoin(request.getTypeSoin());
        creneau.setPlageHoraire(plageHoraireRepository.getReferenceById(request.getPlageHoraireId()));
        return creneau;

    }
}
