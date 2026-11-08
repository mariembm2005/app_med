package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.mapper.MedecinMapper;
import com.example.app_medical.repository.MedecinRepository;
import com.example.app_medical.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private PatientRepository patientRepository;
    private MedecinRepository medecinRepository;
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
}
