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

        // Inherited fields
        updateEntity(medecin, dto);

        // Do not set the ID: the database generates it

        // Specific fields
        medecin.setRole(RoleEnum.MEDECIN);
        medecin.setNumeroOrdre(dto.getNumeroOrdre());
        medecin.setApproved(false);

        // Retrieve the existing specialty
        if (dto.getSpecialite() != null &&
                dto.getSpecialite().getId() != null) {

            Specialite specialite = specialiteRepository
                    .findById(dto.getSpecialite().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Spécialité introuvable"));

            medecin.setSpecialite(specialite);
        } else {
            throw new RuntimeException("Veuillez sélectionner une spécialité");
        }

        return medecin;
    }

    public MedecinResponseDTO toMedecinResponse(Medecin medecin) {
        MedecinResponseDTO dto = new MedecinResponseDTO();

        dto.setId(medecin.getId());
        dto.setNom(medecin.getNom());
        dto.setPrenom(medecin.getPrenom());
        dto.setEmail(medecin.getEmail());
        dto.setApproved(medecin.isApproved());
        dto.setNumeroOrdre(medecin.getNumeroOrdre());
        dto.setSpecialite(medecin.getSpecialite());
        dto.setAdresse(medecin.getAdresse());
        return dto;
    }

    public Medecin toEntity(MedecinRequestDTO dto) {
        return toMedecin(dto);
    }
}