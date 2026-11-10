package com.example.app_medical.Controller;

import com.example.app_medical.dto.MedecinResponseDTO;
import com.example.app_medical.entity.Specialite;
import com.example.app_medical.service.MedecinService;
import com.example.app_medical.service.SpecialiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")

public class AdminController {
    private MedecinService medecinService;
    private SpecialiteService specialiteService;
    public AdminController(MedecinService medecinService, SpecialiteService specialiteService) {
        this.medecinService =  medecinService;
        this.specialiteService =  specialiteService;
    }
    @PutMapping("/{id}/approve")
    public ResponseEntity<MedecinResponseDTO> approve(@PathVariable Long id) {
        return medecinService.approve(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/ajout-specialite")
    public void ajouterSpecialite(@RequestBody Specialite specialite) {
        specialiteService.add(specialite);
    }
}
