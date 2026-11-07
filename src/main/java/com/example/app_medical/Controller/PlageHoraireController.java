package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.service.*;
import lombok.*;
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
}