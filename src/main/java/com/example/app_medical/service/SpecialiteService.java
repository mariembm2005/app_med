package com.example.app_medical.service;

import com.example.app_medical.entity.Specialite;
import com.example.app_medical.repository.SpecialiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialiteService {
    private SpecialiteRepository specialiteRepository;
    public SpecialiteService(SpecialiteRepository specialiteRepository) {
        this.specialiteRepository = specialiteRepository;
    }
    public List<Specialite> findAll() {
        return specialiteRepository.findAll();
    }
    public Specialite findById(Long id) {
        Optional<Specialite> s = specialiteRepository.findById(id);
        return s.orElseThrow(() ->
                new RuntimeException("Specialite not found"));
    }
    public boolean delete(Specialite specialite) {
        Optional<Specialite> s = specialiteRepository.findById(specialite.getId());
        if (s.isPresent()) {
            specialiteRepository.delete(specialite);
            return true;
        }
        return false;
    }
    public Specialite update(Specialite sp) {
        Optional<Specialite> s = specialiteRepository.findById(sp.getId());
        if (s.isPresent()) {
            Specialite specialite = s.get();
            specialite.setId(sp.getId());
            specialite.setNom(sp.getNom());
            specialite.setDescription(sp.getDescription());
            specialiteRepository.save(specialite);
            return specialite;
        }
        return null;

    }
    public Specialite add(Specialite specialite) {
        return specialiteRepository.saveAndFlush(specialite);
    }
}
