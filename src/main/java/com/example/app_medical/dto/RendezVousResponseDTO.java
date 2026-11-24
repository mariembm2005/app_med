package com.example.app_medical.dto;

import com.example.app_medical.entity.Specialite;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class RendezVousResponseDTO {

    private Long id;
    private LocalDate dateReservation;
    private boolean confirme;

    // Infos Patient
    private Long patientId;
    private String patientNom;
    private String patientPrenom;
    private String patientTelephone;

    // Infos Créneau & Médecin
    private Long creneauId;
    private String resumeChatbot;
    private LocalDate dateCreneau;
    private String heureCreneau;
    private String nomMedecin;
    private String prenomMedecin;
}