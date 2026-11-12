package com.example.app_medical.dto;

import com.example.app_medical.entity.Medecin;
import lombok.Data;

import java.time.*;

@Data
public class PlageHoraireRequestDTO {
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Long medecinId;
}