package com.example.app_medical.service;

import com.example.app_medical.dto.MedecinRequestDTO;
import com.example.app_medical.dto.MedecinResponseDTO;
import com.example.app_medical.entity.Medecin;
import com.example.app_medical.entity.Specialite;
import com.example.app_medical.repository.MedecinRepository;
import com.example.app_medical.repository.SpecialiteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MedecinService {

    private final MedecinRepository medecinRepository;
    private final SpecialiteRepository specialiteRepository;

    public MedecinService(MedecinRepository medecinRepository,
                          SpecialiteRepository specialiteRepository) {
        this.medecinRepository = medecinRepository;
        this.specialiteRepository = specialiteRepository;
    }

    public List<MedecinResponseDTO> getAll() {
        return medecinRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<MedecinResponseDTO> getNotApproved() {
        return medecinRepository.findByApproved(false).stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<MedecinResponseDTO> getById(Long id) {
        return medecinRepository.findById(id).map(this::toResponse);
    }

    public MedecinResponseDTO create(MedecinRequestDTO request) {
        Medecin medecin = new Medecin();
        applyRequest(medecin, request);
        return toResponse(medecinRepository.save(medecin));
    }

    public Optional<MedecinResponseDTO> update(Long id, MedecinRequestDTO request) {
        return medecinRepository.findById(id).map(m -> {
            applyRequest(m, request);
            return toResponse(medecinRepository.save(m));
        });
    }

    public boolean delete(Long id) {
        if (!medecinRepository.existsById(id)) {
            return false;
        }
        medecinRepository.deleteById(id);
        return true;
    }

    // ---- mapping ----

    private void applyRequest(Medecin m, MedecinRequestDTO r) {
        m.setNom(r.getNom());
        m.setPrenom(r.getPrenom());
        m.setEmail(r.getEmail());
        m.setApproved(r.isApproved());


        m.setSpecialite(r.getSpecialite());
    }

    private MedecinResponseDTO toResponse(Medecin m) {
        MedecinResponseDTO dto = new MedecinResponseDTO();
        dto.setId(m.getId());
        dto.setNom(m.getNom());
        dto.setPrenom(m.getPrenom());
        dto.setEmail(m.getEmail());
        dto.setApproved(m.isApproved());
        dto.setNumeroOrdre(m.getNumeroOrdre());
        dto.setSpecialite(m.getSpecialite());
        return dto;
    }

    public Optional<MedecinResponseDTO> approve(Long id) {
        return medecinRepository.findById(id)
                .map(m -> approve(m));
    }

    private MedecinResponseDTO approve(Medecin m) {
        m.setApproved(true);
        Medecin saved = medecinRepository.save(m);
        return toResponse(saved);
    }
}