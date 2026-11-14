package com.example.app_medical.repository;

import com.example.app_medical.dto.MedecinResponseDTO;
import com.example.app_medical.entity.Medecin;
import com.example.app_medical.entity.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNumeroOrdre(String numeroOrdre);
    List<Medecin> findBySpecialiteId(Long specialiteId);
    List<Medecin>findByApproved(boolean approved);

    List<Medecin> findByNomContainingIgnoreCase(String nom);

    @Query("SELECT DISTINCT m.specialite FROM Medecin m WHERE m.specialite IS NOT NULL")
    List<Specialite> findDistinctSpecialites();

    @Query("SELECT DISTINCT m.adresse FROM Medecin m WHERE m.adresse IS NOT NULL")
    List<String> findDistinctAdresses();

    List<Medecin> findByAdresse(String adresse);

    List<Medecin> findBySpecialiteIdAndAdresse(Long specialiteId, String adresse);



}