package com.example.app_medical.Controller;

import com.example.app_medical.dto.ForgotPasswordRequestDTO;
import com.example.app_medical.dto.ResetPasswordRequestDTO;
import com.example.app_medical.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDTO dto) {
        passwordResetService.requestReset(dto.getEmail());
        return ResponseEntity.ok(Map.of(
                "message", "Si un compte existe avec cet e-mail, un code a été envoyé."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDTO dto) {
        try {
            passwordResetService.resetPassword(dto);
            return ResponseEntity.ok(Map.of("message", "Mot de passe modifié avec succès."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}