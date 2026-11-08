package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.Creneau;
import com.example.app_medical.service.CreneauService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creneaux")
@RequiredArgsConstructor
public class CreneauController {

    private final CreneauService service;

    @PostMapping
    public CreneauResponseDTO create(@RequestBody CreneauRequestDTO request) {
        return service.create(request);
    }

    @GetMapping
    public List<CreneauResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CreneauResponseDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.ok("delete successfully");
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    public CreneauResponseDTO update(@RequestBody CreneauRequestDTO request, @PathVariable Long id) {
        return service.update(request,id);
    }
    @GetMapping("/disponibles")
    public List<CreneauResponseDTO> getDisponibles() {
        return service.findDisponibles();
    }
    @PatchMapping("/reserver/{id}")
    public CreneauResponseDTO reserver(@PathVariable Long id) {
        return service.reserver(id);
    }
    @PatchMapping("/liberer/{id}")
    public CreneauResponseDTO liberer(@PathVariable Long id) {
        return service.liberer(id);
    }
}
