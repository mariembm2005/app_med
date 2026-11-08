package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rendezvous")
@RequiredArgsConstructor
public class RendezVousController {

    private final RendezVousService rendezVousService;

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
    public ResponseEntity<RendezVousResponseDTO> getById(
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
}
