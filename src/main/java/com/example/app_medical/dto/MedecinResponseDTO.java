package com.example.app_medical.dto;

import com.example.app_medical.dto.UserResponseDTO;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MedecinResponseDTO extends UserResponseDTO {
    private String numeroOrdre;
    private Long specialiteId;
    private String specialiteNom;
}