package com.example.app_medical.dto;

import java.util.List;

public record ChatResponseDTO(
        String reponse,
        List<String> reponsesRapides,
        List<String> specialites,
        boolean termine,
        String resume
) {}