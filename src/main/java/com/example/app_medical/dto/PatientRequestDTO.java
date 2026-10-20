package com.example.app_medical.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PatientRequestDTO extends UserRequestDTO {

    @Min(value = 0, message = "Age invalide")
    private int age;

    @NotBlank(message = "Telephone requis")
    private String telephone;
}