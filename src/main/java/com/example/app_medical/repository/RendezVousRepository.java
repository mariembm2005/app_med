package com.example.app_medical.repository;

import com.example.app_medical.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RendezVousRepository
        extends JpaRepository<RendezVous, Long> {
    Optional<RendezVous> findByCreneauId(Long creneauId);
    List<RendezVous> findByPatientId(Long patientId);
    Optional<RendezVous> findByIdAndPatientId(
            Long rendezVousId,
            Long patientId
    );

}
