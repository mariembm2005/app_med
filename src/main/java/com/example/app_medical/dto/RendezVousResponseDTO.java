package com.example.app_medical.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RendezVousResponseDTO {

    private Long id;
    private LocalDate dateReservation;
    private boolean confirme;
    private Long patientId;
    private Long creneauId;

    // Champs pour l'affichage mobile
    private LocalDate dateCreneau;
    private String heureCreneau;
    private String nomMedecin;
    private String prenomMedecin;
    private String specialite;
    private String cabinet;
}