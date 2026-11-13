package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.RendezVous;
import com.example.app_medical.mapper.RendezVousMapper;
import com.example.app_medical.service.MedecinService;
import com.example.app_medical.service.PatientService;
import com.example.app_medical.service.RendezVousService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PatientController{
    private final RendezVousMapper rendezVousMapper;
    private final PatientService patientService;
    private final MedecinService medecinService;
    private final RendezVousService rendezVousService;
    private final RendezVousController rendezVousController;

    public PatientController(
            PatientService patientService,
            MedecinService medecinService,
            RendezVousMapper rendezVousMapper,
            RendezVousService rendezVousService,
    RendezVousController rendezVousController) {

        this.patientService = patientService;
        this.medecinService = medecinService;
        this.rendezVousMapper = rendezVousMapper;
        this.rendezVousService = rendezVousService;
        this.rendezVousController = rendezVousController;
    }@GetMapping("/rechercher-medecin/{nom}")
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
    @PatchMapping("/{id}/confirmer")
    public ResponseEntity<String> confirmer(
            @PathVariable Long id) {
        RendezVous r = rendezVousService.confirmer(id);
        if(r!=null) {
            r.setConfirme(true);
            return ResponseEntity.ok(
                    "Rendez vous"+r.getId()+" est confirmé"
            );

        }
        return ResponseEntity.badRequest().build();

    }
    @PatchMapping("/{id}/annuler")
    public ResponseEntity<String> annuler(
            @PathVariable Long id) {

        RendezVous r = rendezVousService.annuler(id);
        if(r!=null) {
            return ResponseEntity.ok(
                    "Rendez vous "+ id + " est annulé"
            );

        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/medecin/{id}/creneaux")
    public List<CreneauResponseDTO> consulterCreneaux(
            @PathVariable Long id){

        return patientService.consulterCreneaux(id);
    }


        @PostMapping("/rendez-vous/reserver")
        public ResponseEntity<?> reserver(
                @RequestBody RendezVousRequestDTO dto) {

            try {
                return ResponseEntity.ok(
                        rendezVousService.reserver(dto)
                );
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest()
                        .body(e.getMessage());
            }
        }



}
