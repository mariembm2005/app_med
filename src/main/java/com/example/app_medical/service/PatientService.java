package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.Creneau;
import com.example.app_medical.entity.Patient;
import com.example.app_medical.entity.RendezVous;
import com.example.app_medical.mapper.CreneauMapper;
import com.example.app_medical.mapper.MedecinMapper;
import com.example.app_medical.mapper.PatientMapper;
import com.example.app_medical.mapper.RendezVousMapper;
import com.example.app_medical.repository.CreneauRepository;
import com.example.app_medical.repository.MedecinRepository;
import com.example.app_medical.repository.PatientRepository;
import com.example.app_medical.repository.RendezVousRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientMapper patientMapper;
    private PatientRepository patientRepository;
    private MedecinRepository medecinRepository;
    private CreneauRepository creneauRepository;
    private final CreneauService creneauService;
    private final RendezVousMapper rendezVousMapper;
    private final RendezVousRepository rendezVousRepository;
    private CreneauMapper creneauMapper;
    private MedecinMapper medecinMapper;
    public List<MedecinResponseDTO> rechercherMedecin(String nom) {
            return medecinRepository.findByNomContainingIgnoreCase(nom)
                    .stream()
                    .map(medecinMapper::toMedecinResponse)
                    .toList();
        }

    public List<MedecinResponseDTO> filtrerParSpecialite(Long id) {

        return medecinRepository.findBySpecialiteId(id)
                .stream()
                .map(medecinMapper::toMedecinResponse)
                .toList();
    }

    public List<CreneauResponseDTO> consulterCreneaux(Long medecinId) {

        return creneauRepository
                .findByPlageHoraireMedecinId(medecinId)
                .stream()
                .map(creneauMapper::toDTO)
                .toList();
    }
    @Transactional
    public RendezVousResponseDTO reserver(RendezVousRequestDTO dto) {

        Creneau creneau = creneauRepository.findById(dto.getCreneauId())
                .orElseThrow(() ->
                        new RuntimeException("Créneau introuvable"));

        if (!Boolean.TRUE.equals(creneau.getDisponible())) {
            throw new RuntimeException(
                    "Impossible de réserver : ce créneau n'est pas disponible."
            );
        }

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() ->
                        new RuntimeException("Patient introuvable"));

        RendezVous rendezVous = new RendezVous();

        rendezVous.setCreneau(creneau);
        rendezVous.setPatient(patient);
        rendezVous.setDateReservation(LocalDate.now());
        rendezVous.setConfirme(true);

        RendezVous saved = rendezVousRepository.save(rendezVous);

        creneau.setDisponible(false);
        creneauRepository.save(creneau);

        return rendezVousMapper.toDTO(saved);
    }
}
