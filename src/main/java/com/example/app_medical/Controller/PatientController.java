package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.service.MedecinService;
import com.example.app_medical.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientController {
    private PatientService patientService;
    private RendezVousController rendezVousController;
    private MedecinService medecinService;
    @GetMapping("/rechercher-medecin/{nom}")
    public List<MedecinResponseDTO> rechercher(
            @PathVariable String nom) {

        return patientService.rechercherMedecin(nom);
    }
    @PostMapping
    public ResponseEntity<RendezVousResponseDTO> create(
            @RequestBody RendezVousRequestDTO dto) {

        return rendezVousController.create(dto);
    }
    @GetMapping("/specialite/{id}")
    public List<MedecinResponseDTO> filtrer(
            @PathVariable Long id) {

        return patientService.filtrerParSpecialite(id);
    }
}
