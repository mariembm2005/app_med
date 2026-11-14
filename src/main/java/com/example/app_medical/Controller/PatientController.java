package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.service.CreneauService;
import com.example.app_medical.service.MedecinService;
import com.example.app_medical.service.PatientService;
import com.example.app_medical.service.RendezVousService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController // ⬅️ TRÈS IMPORTANT : @RestController (et non @Controller) pour du JSON
@RequestMapping("/api/patient") // ⬅️ TRÈS IMPORTANT : Pour matcher avec l'app mobile
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    private final PatientService patientService;
    private final RendezVousService rendezVousService;
    private final MedecinService medecinService;
    private final CreneauService creneauService;

    @GetMapping("/rechercher-medecin/{nom}")
    public List<MedecinResponseDTO> rechercher(@PathVariable String nom) {
        return patientService.rechercherMedecin(nom);
    }

    @GetMapping("/specialite/{id}")
    public List<MedecinResponseDTO> filtrer(@PathVariable Long id) {
        return patientService.filtrerParSpecialite(id);
    }

    @PatchMapping("/{id}/confirmer")
    public ResponseEntity<String> confirmer(@PathVariable Long id) {
        RendezVousResponseDTO r = rendezVousService.getById(id);
        if(r != null) {
            r.setConfirme(true);
            return ResponseEntity.ok("Rendez vous " + r.getId() + " est confirmé");
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/annuler")
    public ResponseEntity<String> annuler(@PathVariable Long id) {
        RendezVousResponseDTO r = rendezVousService.getById(id);
        if(r != null) {
            r.setConfirme(false);
            return ResponseEntity.ok("Rendez vous " + r.getId() + " est annulé");
        }
        return ResponseEntity.badRequest().build();
    }



    @GetMapping("/mes-rendezvous")
    public List<RendezVousResponseDTO> getMesRendezVous() {
        return rendezVousService.getAll();
    }

    @DeleteMapping("/rendezvous/{id}")
    public ResponseEntity<Void> supprimerRendezVous(@PathVariable Long id) {
        if (rendezVousService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/medecin/{id}/creneaux")
    public List<CreneauResponseDTO> getCreneauxByMedecin(
            @PathVariable Long id,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return creneauService.getByMedecinAndDate(id, date);
    }
}