package com.example.app_medical.mapper;

import com.example.app_medical.dto.RendezVousRequestDTO;
import com.example.app_medical.dto.RendezVousResponseDTO;
import com.example.app_medical.entity.*;
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

    public RendezVousResponseDTO toDTO(RendezVous rdv) {
        if (rdv == null) {
            return null;
        }

        RendezVousResponseDTO dto = new RendezVousResponseDTO();
        dto.setId(rdv.getId());
        dto.setDateReservation(rdv.getDateReservation());
        dto.setConfirme(rdv.isConfirme());
        dto.setResumeChatbot(rdv.getResumeChatbot());
        dto.setRefuse(rdv.isRefuse());

        if (rdv.getPatient() != null) {
            Patient patient = rdv.getPatient();
            dto.setPatientId(patient.getId());
            dto.setPatientNom(patient.getNom());
            dto.setPatientPrenom(patient.getPrenom());
            dto.setPatientTelephone(patient.getTelephone());
        }

        if (rdv.getCreneau() != null) {
            Creneau creneau = rdv.getCreneau();
            dto.setCreneauId(creneau.getId());

            if (creneau.getHeureDebut() != null && creneau.getHeureFin() != null) {
                dto.setHeureCreneau(creneau.getHeureDebut() + " - " + creneau.getHeureFin());
            } else if (creneau.getHeureDebut() != null) {
                dto.setHeureCreneau(creneau.getHeureDebut().toString());
            }

            if (creneau.getPlageHoraire() != null) {
                PlageHoraire plage = creneau.getPlageHoraire();
                dto.setDateCreneau(plage.getDate());

                if (plage.getMedecin() != null) {
                    Medecin medecin = plage.getMedecin();
                    dto.setNomMedecin(medecin.getNom());
                    dto.setPrenomMedecin(medecin.getPrenom());
                }
            }
        }

        return dto;
    }
}