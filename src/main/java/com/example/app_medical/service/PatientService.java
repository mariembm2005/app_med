package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.mapper.CreneauMapper;
import com.example.app_medical.mapper.MedecinMapper;
import com.example.app_medical.repository.CreneauRepository;
import com.example.app_medical.repository.MedecinRepository;
import com.example.app_medical.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final CreneauRepository creneauRepository;
    private final CreneauMapper creneauMapper;
    private final MedecinMapper medecinMapper;
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
                .map(creneauMapper::toResponse)
                .toList();
    }
}
