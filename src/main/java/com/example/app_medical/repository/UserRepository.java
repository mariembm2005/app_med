package com.example.app_medical.repository;

import com.example.app_medical.entity.RoleEnum;
import com.example.app_medical.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findByRole(RoleEnum role);

}
