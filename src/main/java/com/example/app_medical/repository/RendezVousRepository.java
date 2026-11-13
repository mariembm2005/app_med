package com.example.app_medical.repository;

import com.example.app_medical.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RendezVousRepository
        extends JpaRepository<RendezVous, Long> {
    Optional<RendezVous> findByCreneauId(Long creneauId);

}
