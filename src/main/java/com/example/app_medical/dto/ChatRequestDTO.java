package com.example.app_medical.dto;

import java.util.List;

public record ChatRequestDTO(
        Long patientId,
        Long rendezVousId,
        List<ChatMessageDTO> historique
) {
}