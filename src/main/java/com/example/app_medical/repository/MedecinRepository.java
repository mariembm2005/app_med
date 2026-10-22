package com.example.app_medical.repository;

import com.example.app_medical.entity.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNumeroOrdre(String numeroOrdre);
    List<Medecin> findBySpecialiteId(Long specialiteId);
    List<Medecin>findByApproved(boolean approved);
}