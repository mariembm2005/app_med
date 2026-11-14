package com.example.app_medical.dto;

import com.example.app_medical.entity.RoleEnum;
import com.example.app_medical.entity.SexeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private Long id;
    @NotBlank(message = "Nom requi")
    private String nom;
    @NotBlank(message = "Prenom requi")
    private String prenom;
    @NotBlank(message = "Email requi")
    @Email(message = "Email doit etre valide")
    private String email;

    @NotBlank(message = "Mot de passe requi")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String motDePasse;

    @NotBlank(message = "Confirmer Mot de passe requi")

    private RoleEnum role;

    private SexeEnum sexe;
    private java.time.LocalDate dateNaissance;

    private String image;
   }
