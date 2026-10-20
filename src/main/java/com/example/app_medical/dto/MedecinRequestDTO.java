package com.example.app_medical.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MedecinRequestDTO extends UserRequestDTO {

    @NotBlank(message = "Numero d'ordre requis")
    private String numeroOrdre;

    @NotNull(message = "Specialite requise")
    private Long specialiteId;
}
