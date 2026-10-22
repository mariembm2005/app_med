package com.example.app_medical.dto;

import com.example.app_medical.dto.UserResponseDTO;
import com.example.app_medical.entity.Specialite;
import lombok.*;

@Data
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class MedecinResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private boolean approved;
    private String numeroOrdre;
    private Specialite specialite;


}