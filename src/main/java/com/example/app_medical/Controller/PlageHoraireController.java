package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.mapper.PlageHoraireMapper;
import com.example.app_medical.service.*;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plages-horaires")
@RequiredArgsConstructor
public class PlageHoraireController {

    private final PlageHoraireService service;

    @PostMapping
    public PlageHoraireResponseDTO create(
            @RequestBody PlageHoraireRequestDTO request) {

        return service.create(request);
    }

    @GetMapping
    public List<PlageHoraireResponseDTO> getAll() {

        return service.getAll();
    }
    @GetMapping("/{id}")
    public PlageHoraireResponseDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }
    @PutMapping("/{id}")
    public PlageHoraireResponseDTO update(@RequestBody PlageHoraireRequestDTO request, @PathVariable Long id) {
        return service.update(request,id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        if (service.delete(id)) {
            return ResponseEntity.ok("plage horaire deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}