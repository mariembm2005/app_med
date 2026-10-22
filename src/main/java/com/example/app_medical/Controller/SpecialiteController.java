package com.example.app_medical.Controller;

import com.example.app_medical.entity.Specialite;
import com.example.app_medical.repository.SpecialiteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialites")
@CrossOrigin(origins = "http://localhost:4200")
public class SpecialiteController {

    private final SpecialiteRepository specialiteRepository;

    public SpecialiteController(SpecialiteRepository specialiteRepository) {
        this.specialiteRepository = specialiteRepository;
    }

    @GetMapping
    public List<Specialite> getAll() {
        return specialiteRepository.findAll();
    }
}