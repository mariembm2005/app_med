package com.example.app_medical.Controller;

import com.example.app_medical.Security.JwtUtil;
import com.example.app_medical.dto.MedecinRequestDTO;
import com.example.app_medical.dto.MedecinResponseDTO;
import com.example.app_medical.service.MedecinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
public class MedecinController {

    private final MedecinService medecinService;
    private JwtUtil  jwtUtil;
    public MedecinController(MedecinService medecinService, JwtUtil jwtUtil) {
        this.medecinService = medecinService;
        this.jwtUtil=jwtUtil;
    }


    @GetMapping
    public List<MedecinResponseDTO> getAll() {
        if(jwtUtil!=null){
            System.out.println(medecinService.getAll());
        return medecinService.getAll();}
        return null;
    }


    @GetMapping("/not-approved")
    public List<MedecinResponseDTO> getNotApproved() {
        return medecinService.getNotApproved();
    }

    // GET: one medecin by id
    @GetMapping("/{id}")
    public ResponseEntity<MedecinResponseDTO> getById(@PathVariable Long id) {
        return medecinService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: create
    @PostMapping
    public ResponseEntity<MedecinResponseDTO> create(@RequestBody MedecinRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medecinService.create(request));
    }

    // PUT: update
    @PutMapping("/{id}")
    public ResponseEntity<MedecinResponseDTO> update(@PathVariable Long id,
                                                     @RequestBody MedecinRequestDTO request) {
        return medecinService.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return medecinService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<MedecinResponseDTO> approve(@PathVariable Long id) {
        return medecinService.approve(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}