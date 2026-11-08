package com.example.app_medical.Controller;

import com.example.app_medical.entity.Specialite;
import com.example.app_medical.repository.SpecialiteRepository;
import com.example.app_medical.service.SpecialiteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialites")
@CrossOrigin(origins = "http://localhost:4200")
public class SpecialiteController {

    private final SpecialiteService specialiteService;

    public SpecialiteController(SpecialiteService specialiteService) {
        this.specialiteService = specialiteService;
    }

    @GetMapping
    public List<Specialite> getAll() {
        return specialiteService.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Specialite getById(@PathVariable Long id) {
        return specialiteService.findById(id);
    }

    // ADD
    @PostMapping
    public Specialite add(@RequestBody Specialite specialite) {
        return specialiteService.add(specialite);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Specialite update(
            @PathVariable Long id,
            @RequestBody Specialite specialite) {

        specialite.setId(id);
        return specialiteService.update(specialite);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {

        Specialite specialite = specialiteService.findById(id);
        return specialiteService.delete(specialite);
    }
}