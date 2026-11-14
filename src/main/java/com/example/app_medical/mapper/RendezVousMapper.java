package com.example.app_medical.mapper;

import com.example.app_medical.dto.RendezVousRequestDTO;
import com.example.app_medical.dto.RendezVousResponseDTO;
import com.example.app_medical.entity.Creneau;
import com.example.app_medical.entity.Medecin;
import com.example.app_medical.entity.PlageHoraire;
import com.example.app_medical.entity.RendezVous;
import com.example.app_medical.repository.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
        entity.setCreneau(creneauRepository.getReferenceById(dto.getCreneauId()));
        entity.setDateReservation(LocalDate.now());
        return entity;
    }

    public RendezVousResponseDTO toDTO(RendezVous entity) {
        RendezVousResponseDTO dto = new RendezVousResponseDTO();
        dto.setId(entity.getId());
        dto.setConfirme(entity.isConfirme());
        dto.setDateReservation(entity.getDateReservation());

        if (entity.getPatient() != null) {
            dto.setPatientId(entity.getPatient().getId());
        }

        Creneau c = entity.getCreneau();
        if (c != null) {
            dto.setCreneauId(c.getId());

            if (c.getHeureDebut() != null && c.getHeureFin() != null) {
                dto.setHeureCreneau(c.getHeureDebut() + " - " + c.getHeureFin());
            } else if (c.getHeureDebut() != null) {
                dto.setHeureCreneau(c.getHeureDebut().toString());
            }

            PlageHoraire p = c.getPlageHoraire();
            if (p != null) {
                dto.setDateCreneau(p.getDate());               // la date qui manquait

                Medecin m = p.getMedecin();
                if (m != null) {
                    dto.setNomMedecin(m.getPrenom() + " " + m.getNom());   // "Sami ben Ali"
                    dto.setPrenomMedecin(m.getPrenom());
                    if (m.getSpecialite() != null) {
                        dto.setSpecialite(m.getSpecialite().getNom());
                    }
                    dto.setCabinet(m.getAdresse());            // voir le point ci-dessous
                }
            }
        }

        if (dto.getCabinet() == null || dto.getCabinet().isBlank()) {
            dto.setCabinet("Cabinet médical");
        }
        return dto;
    }
}