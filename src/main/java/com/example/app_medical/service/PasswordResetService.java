package com.example.app_medical.service;

import com.example.app_medical.dto.ResetPasswordRequestDTO;
import com.example.app_medical.entity.PasswordResetToken;
import com.example.app_medical.entity.Utilisateur;
import com.example.app_medical.repository.PasswordResetTokenRepository;
import com.example.app_medical.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class PasswordResetService {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetService.class);
    private static final int MAX_ATTEMPTS = 5;
    private static final String INVALID = "Code invalide ou expiré.";

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.reset-token-expiration-minutes:15}")
    private long expirationMinutes;

    public PasswordResetService(UserRepository userRepository,
                                PasswordResetTokenRepository tokenRepository,
                                PasswordEncoder passwordEncoder,
                                EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    /** Ne révèle jamais si l'e-mail existe ou non. */
    @Transactional
    public void requestReset(String email) {
        if (email == null || email.isBlank()) return;

        userRepository.findByEmail(email.trim()).ifPresent(user -> {
            tokenRepository.deleteByUser(user);
            tokenRepository.flush();

            String code = String.format("%06d", secureRandom.nextInt(1_000_000));

            PasswordResetToken token = new PasswordResetToken();
            token.setTokenHash(passwordEncoder.encode(code));
            token.setUser(user);
            token.setExpiryDate(LocalDateTime.now().plusMinutes(expirationMinutes));
            tokenRepository.save(token);

            // DEV (facultatif, à supprimer ensuite) : voir le code dans la console
            // log.info("DEV - code pour {} : {}", user.getEmail(), code);

            try {
                emailService.sendResetCode(user.getEmail(), user.getPrenom(), code);
            } catch (Exception e) {
                log.error("Échec d'envoi de l'e-mail de réinitialisation", e);
            }
        });
    }

    // noRollbackFor : le compteur d'essais doit être enregistré même quand on lance l'exception
    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public void resetPassword(ResetPasswordRequestDTO dto) {
        if (dto.getEmail() == null || dto.getCode() == null || dto.getMotDePasse() == null) {
            throw new IllegalArgumentException("Requête invalide.");
        }
        if (dto.getMotDePasse().length() < 8) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères.");
        }

        Utilisateur user = userRepository.findByEmail(dto.getEmail().trim())
                .orElseThrow(() -> new IllegalArgumentException(INVALID));

        PasswordResetToken token = tokenRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException(INVALID));

        if (token.isUsed() || token.isExpired()) {
            throw new IllegalArgumentException(INVALID);
        }
        if (token.getAttempts() >= MAX_ATTEMPTS) {
            throw new IllegalArgumentException("Trop de tentatives. Demandez un nouveau code.");
        }

        if (!passwordEncoder.matches(dto.getCode().trim(), token.getTokenHash())) {
            token.setAttempts(token.getAttempts() + 1);
            tokenRepository.save(token);
            throw new IllegalArgumentException(INVALID);
        }

        user.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);
    }
}