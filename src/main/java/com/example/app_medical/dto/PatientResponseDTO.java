package com.example.app_medical.dto;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PatientResponseDTO extends UserResponseDTO {
    private int age;
    private String telephone;
}