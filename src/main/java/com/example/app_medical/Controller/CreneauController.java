package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.service.CreneauService;
import lombok.RequiredArgsConstructor;
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
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
