package com.example.app_medical.Controller;

import com.example.app_medical.dto.PlageHoraireRequestDTO;
import com.example.app_medical.entity.PlageHoraire;
import com.example.app_medical.service.PlageHoraireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plages-horaires")
@CrossOrigin(origins = "http://localhost:4200")
public class PlageHoraireController {

    private final PlageHoraireService plageHoraireService;

    public PlageHoraireController(
            PlageHoraireService plageHoraireService
    ) {
        this.plageHoraireService = plageHoraireService;
    }

    @GetMapping
    public ResponseEntity<List<PlageHoraire>> liste() {
        return ResponseEntity.ok(plageHoraireService.getAll());
    }

    @PostMapping
    public ResponseEntity<PlageHoraire> create(
            @RequestBody PlageHoraireRequestDTO dto
    ) {
        return ResponseEntity.ok(plageHoraireService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlageHoraire> update(
            @PathVariable("id") Long id,
            @RequestBody PlageHoraireRequestDTO dto
    ) {
        return ResponseEntity.ok(
                plageHoraireService.update(id,dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable("id") Long id
    ) {
        plageHoraireService.delete(id);
        return ResponseEntity.ok("Plage horaire supprimée");
    }
}