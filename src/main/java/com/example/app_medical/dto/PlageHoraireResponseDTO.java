package com.example.app_medical.dto;

import lombok.Data;

import java.time.*;

@Data
public class PlageHoraireResponseDTO {

    private Long id;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
}
