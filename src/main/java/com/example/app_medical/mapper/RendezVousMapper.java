package com.example.app_medical.mapper;

import com.example.app_medical.dto.RendezVousRequestDTO;
import com.example.app_medical.dto.RendezVousResponseDTO;
import com.example.app_medical.entity.RendezVous;
import com.example.app_medical.repository.*;
import com.example.app_medical.repository.PatientRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class RendezVousMapper {
    private final PatientRepository patientRepository;
    private final CreneauRepository creneauRepository;

    public RendezVousMapper(PatientRepository patientRepository, CreneauRepository creneauRepository) {
        this.patientRepository = patientRepository;
        this.creneauRepository = creneauRepository;
    }

    public RendezVous toEntity(RendezVousRequestDTO dto) {
        RendezVous entity = new RendezVous();
        entity.setId(dto.getId());
        entity.setPatient(patientRepository.getReferenceById(dto.getPatientId()));
        entity.setCreneau(creneauRepository.getReferenceById(dto.getCreneauId())
        );
        entity.setDateReservation(LocalDate.now());
        return entity;
    }
    public RendezVousResponseDTO toDTO(RendezVous entity) {
        RendezVousResponseDTO dto = new RendezVousResponseDTO();
        dto.setId(entity.getId());
        dto.setConfirme(entity.isConfirme());
        dto.setCreneauId(entity.getCreneau().getId());
        dto.setPatientId(entity.getPatient().getId());
        dto.setDateReservation(entity.getDateReservation());
        return dto;

    }
}
