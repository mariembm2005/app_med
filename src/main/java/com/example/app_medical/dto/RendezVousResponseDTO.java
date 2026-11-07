package com.example.app_medical.dto;

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

    private Long patientId;

    private Long creneauId;
}
