package com.example.app_medical.dto;

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



    private String patientNom;
    private String patientPrenom;
    private String patientTelephone;
    private String motif;

    // new: needed to place the créneau in the planning
    private LocalDate date;
    private Long plageId;
    private Long medecinId;
    private String medecinNom;
}
