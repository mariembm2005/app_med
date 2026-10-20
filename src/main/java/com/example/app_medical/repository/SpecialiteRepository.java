package com.example.app_medical.repository;

import com.example.app_medical.entity.Specialite;
import com.example.app_medical.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {
    Optional<Specialite> findByNom(String nom);
    boolean existsByNom(String nom);
}
