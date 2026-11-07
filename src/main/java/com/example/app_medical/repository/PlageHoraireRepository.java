package com.example.app_medical.repository;

import com.example.app_medical.entity.PlageHoraire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlageHoraireRepository
        extends JpaRepository<PlageHoraire, Long> {
}
