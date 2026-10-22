package com.example.app_medical.dto;

import com.example.app_medical.entity.RoleEnum;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String token;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    private String nom;
    private String prenom;
    private String email;
    private String image;
    private RoleEnum role;
}