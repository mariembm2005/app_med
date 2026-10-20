package com.example.app_medical.mapper;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import org.springframework.stereotype.*;

@Component
public class MedecinMapper extends UserMapper {

    public Medecin toMedecin(MedecinRequestDTO dto) {
        Medecin medecin = new Medecin();
        updateEntity(medecin, dto);
        medecin.setRole(RoleEnum.MEDECIN);
        medecin.setNumeroOrdre(dto.getNumeroOrdre());
        return medecin;
    }

    public MedecinResponseDTO toMedecinResponse(Medecin medecin) {
        MedecinResponseDTO dto = new MedecinResponseDTO();
        fillResponse(dto, medecin);
        dto.setNumeroOrdre(medecin.getNumeroOrdre());
        if (medecin.getSpecialite() != null) {
            dto.setSpecialiteId(medecin.getSpecialite().getId());
            dto.setSpecialiteNom(medecin.getSpecialite().getNom());
        }
        return dto;
    }

    public Medecin toEntity(MedecinRequestDTO dto) {
        Medecin medecin = new Medecin();
        updateEntity(medecin, dto);
        medecin.setRole(RoleEnum.MEDECIN);
        medecin.setNumeroOrdre(dto.getNumeroOrdre());
        return medecin;
    }
}