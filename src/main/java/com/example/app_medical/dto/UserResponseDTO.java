package com.example.app_medical.dto;

import com.example.app_medical.entity.RoleEnum;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String nom;
    private String prenom;
    private String email;
    private String image;
    private RoleEnum role;
}