package com.example.app_medical.repository;

import com.example.app_medical.entity.PasswordResetToken;
import com.example.app_medical.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByUser(Utilisateur user);
    void deleteByUser(Utilisateur user);
}