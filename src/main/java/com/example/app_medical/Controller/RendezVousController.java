package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.Creneau;
import com.example.app_medical.entity.Patient;
import com.example.app_medical.entity.RendezVous;
import com.example.app_medical.mapper.RendezVousMapper;
import com.example.app_medical.repository.CreneauRepository;
import com.example.app_medical.repository.PatientRepository;
import com.example.app_medical.repository.RendezVousRepository;
import com.example.app_medical.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rendezvous")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class RendezVousController {

    private final RendezVousService rendezVousService;
    private final RendezVousRepository rendezVousRepository;
    private final CreneauRepository creneauRepository;
    private final PatientRepository patientRepository;
    private final RendezVousMapper rendezVousMapper;

    @PostMapping
    public ResponseEntity<RendezVousResponseDTO> create(
            @RequestBody RendezVousRequestDTO dto) {

        return ResponseEntity.ok(
                rendezVousService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<RendezVousResponseDTO>> getAll() {

        return ResponseEntity.ok(
                rendezVousService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RendezVous> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                rendezVousService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVousResponseDTO> update(
            @PathVariable Long id,
            @RequestBody RendezVousRequestDTO dto) {

        return ResponseEntity.ok(
                rendezVousService.update(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        if(rendezVousService.delete(id)){
            return ResponseEntity.ok("deleted successfully");
        }

        return ResponseEntity.noContent().build();
    }
    @Transactional
    public RendezVousResponseDTO reserver(RendezVousRequestDTO dto) {

        // 1. Vérifier le créneau
        Creneau creneau = creneauRepository.findById(dto.getCreneauId())
                .orElseThrow(() ->
                        new RuntimeException("Créneau introuvable"));

        // 2. Vérifier sa disponibilité
        if (!creneau.getDisponible()) {
            throw new RuntimeException(
                    "Ce créneau n'est plus disponible");
        }

        // 3. Récupérer le patient
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() ->
                        new RuntimeException("Patient introuvable"));

        // 4. Créer le rendez-vous
        RendezVous rendezVous = new RendezVous();

        rendezVous.setCreneau(creneau);
        rendezVous.setPatient(patient);
        rendezVous.setConfirme(false);

        // 5. Enregistrer
        RendezVous saved = rendezVousRepository.save(rendezVous);

        // 6. Rendre le créneau indisponible
        creneau.setDisponible(false);
        creneauRepository.save(creneau);

        return rendezVousMapper.toDTO(saved);
    }

}
