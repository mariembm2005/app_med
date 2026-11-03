package com.example.app_medical.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO {
    private String email;
    private String code;
    private String motDePasse;
}