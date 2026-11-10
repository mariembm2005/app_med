package com.example.app_medical.Controller;

import com.example.app_medical.Security.JwtUtil;
import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.service.TokenBlacklistService;
import com.example.app_medical.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final PatientMapper patientMapper;
    private final MedecinMapper medecinMapper;
    private final TokenBlacklistService tokenBlacklistService;


    public AuthController(UserService userService,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil,
                          PatientMapper patientMapper,
                          MedecinMapper medecinMapper,TokenBlacklistService tokenBlacklistService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.patientMapper = patientMapper;
        this.medecinMapper = medecinMapper;
        this.tokenBlacklistService = tokenBlacklistService;
    }



    @PostMapping("/signup/patient")
    public ResponseEntity<?> signupPatient(
            @RequestBody PatientRequestDTO dto) {

        ResponseEntity<?> error = checkSignup(dto);
        if (error != null) {
            return error;
        }
        if (dto.getSexe() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Le sexe est obligatoire."));
        }
        if (dto.getDateNaissance() == null
                || dto.getDateNaissance().isAfter(java.time.LocalDate.now())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Date de naissance invalide."));
        }

        Patient saved = userService.createPatient(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientMapper.toPatientResponse(saved));
    }

    @PostMapping("/signup/medecin")
    public ResponseEntity<?> signupMedecin(@RequestBody MedecinRequestDTO dto) {
        ResponseEntity<?> error = checkSignup(dto);
        if (error != null) return error;

        Medecin saved = userService.createMedecin(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medecinMapper.toMedecinResponse(saved));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO request) {

        Utilisateur user = userService.getUserByEmail(request.getEmail());

        if (user == null
                || !passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "E-mail ou mot de passe incorrect."));
        }

        if (user instanceof Medecin medecin && !medecin.isApproved()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Votre compte n'a pas encore été approuvé."));
        }

        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setNom(user.getNom());
        response.setPrenom(user.getPrenom());
        response.setEmail(user.getEmail());
        response.setImage(user.getImage());
        response.setRole(user.getRole());
        response.setToken(jwtUtil.generateToken(user.getEmail(), user.getRole().toString()));



        return ResponseEntity.ok(response);
    }

    private ResponseEntity<?> checkSignup(UserRequestDTO dto) {
        if (userService.getUserByEmail(dto.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already exists"));
        }
        return null;
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            // Ajouter le token dans une blacklist
            tokenBlacklistService.blacklistToken(token);
        }

        return ResponseEntity.ok("Déconnexion réussie");
    }
}