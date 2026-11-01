package com.example.app_medical.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hash BCrypt du code à 6 chiffres (jamais le code en clair)
    @Column(nullable = false, unique = true, length = 64)
    private String tokenHash;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    private boolean used = false;

    // Nombre de codes faux déjà saisis
    private int attempts = 0;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}