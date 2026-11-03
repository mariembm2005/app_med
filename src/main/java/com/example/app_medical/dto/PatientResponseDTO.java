package com.example.app_medical.dto;

import com.example.app_medical.entity.SexeEnum;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PatientResponseDTO extends UserResponseDTO {
    private int age;
    private String telephone;

    private SexeEnum sexe;
    private java.time.LocalDate dateNaissance;
}