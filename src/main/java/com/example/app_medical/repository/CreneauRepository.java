package com.example.app_medical.repository;

import com.example.app_medical.entity.Creneau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreneauRepository extends JpaRepository<Creneau, Long> {
    List<Creneau> findByDisponibleTrue();
    List<Creneau> findByPlageHoraireMedecinId(Long medecinId);
}
