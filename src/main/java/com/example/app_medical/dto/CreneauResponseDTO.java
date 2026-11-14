package com.example.app_medical.dto;

import com.example.app_medical.entity.PlageHoraire;
import com.example.app_medical.entity.RendezVous;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreneauResponseDTO {

    private Long id;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private boolean disponible;
    private String typeSoin;
    private LocalDate date;
}
