package com.example.app_medical.dto;

import com.example.app_medical.entity.PlageHoraire;
import com.example.app_medical.entity.RendezVous;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalTime;

@Data
public class CreneauRequestDTO {
    private Long id;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String typeSoin;
    private Long plageHoraireId;

}
