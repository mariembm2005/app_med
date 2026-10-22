package com.example.app_medical.mapper;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.repository.SpecialiteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedecinMapper extends UserMapper {

    private final SpecialiteRepository specialiteRepository;

    public Medecin toMedecin(MedecinRequestDTO dto) {
        Medecin medecin = new Medecin();

        // Fields inherited from User
        updateEntity(medecin, dto);
        medecin.setId(dto.getId());

        // Fields specific to Medecin
        medecin.setRole(RoleEnum.MEDECIN);
        medecin.setNumeroOrdre(dto.getNumeroOrdre());
        medecin.setApproved(dto.isApproved());



            medecin.setSpecialite(dto.getSpecialite());


        return medecin;
    }

    public MedecinResponseDTO toMedecinResponse(Medecin medecin) {
        MedecinResponseDTO dto = new MedecinResponseDTO();

        // Fields inherited from User
        dto.setId(medecin.getId());
        dto.setNom(medecin.getNom());
        dto.setPrenom(medecin.getPrenom());
        dto.setEmail(medecin.getEmail());

        // Fields specific to Medecin
        dto.setApproved(medecin.isApproved());
        dto.setNumeroOrdre(medecin.getNumeroOrdre());

        dto.setSpecialite(medecin.getSpecialite());

        return dto;
    }

    public Medecin toEntity(MedecinRequestDTO dto) {
        return toMedecin(dto);
    }
}