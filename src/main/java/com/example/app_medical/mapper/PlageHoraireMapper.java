package com.example.app_medical.mapper;

import com.example.app_medical.dto.PlageHoraireRequestDTO;
import com.example.app_medical.dto.PlageHoraireResponseDTO;
import com.example.app_medical.entity.PlageHoraire;
import com.example.app_medical.repository.MedecinRepository;
import com.example.app_medical.repository.PlageHoraireRepository;
import org.springframework.stereotype.Component;

@Component
public class PlageHoraireMapper {
    private final PlageHoraireRepository plageHoraireRepository;
    private final MedecinRepository medecinRepository;
    public PlageHoraireMapper(PlageHoraireRepository plageHoraireRepository, MedecinRepository medecinRepository) {
        this.plageHoraireRepository = plageHoraireRepository;
        this.medecinRepository = medecinRepository;
    }

    public PlageHoraire toEntity(PlageHoraireRequestDTO request){
        PlageHoraire plageHoraire = new PlageHoraire();
        plageHoraire.setDate(request.getDate());
        plageHoraire.setHeureDebut(request.getHeureDebut());
        plageHoraire.setHeureFin(request.getHeureFin());
        plageHoraire.setMedecin(medecinRepository.getReferenceById(request.getMedecinId()));
        return plageHoraire;
    }

    public PlageHoraireResponseDTO toResponse(PlageHoraire entity){
        PlageHoraireResponseDTO responseDTO = new PlageHoraireResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setDate(entity.getDate());
        responseDTO.setHeureDebut(entity.getHeureDebut());
        responseDTO.setHeureFin(entity.getHeureFin());
        return responseDTO;

    }
}
