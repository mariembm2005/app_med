package com.example.app_medical.repository;

import com.example.app_medical.entity.PlageHoraire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlageHoraireRepository
        extends JpaRepository<PlageHoraire, Long> {
    List<PlageHoraire> findByMedecinIdAndDate(
            Long medecinId,
            LocalDate date
    );
}
