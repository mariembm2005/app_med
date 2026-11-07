package com.example.app_medical.dto;

import com.example.app_medical.entity.*;
import lombok.Data;

@Data
public class RendezVousRequestDTO {
    private Long id;
    private Long patientId;
    private Long creneauId;
}
