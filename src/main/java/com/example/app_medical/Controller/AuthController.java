package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    MedecinMapper medecinMapper;
    PatientMapper patientMapper;


    @PostMapping("/signup/patient")
    public ResponseEntity<?> signupPatient(@RequestBody PatientRequestDTO dto) {
        ResponseEntity<?> error = checkSignup(dto);
        if (error != null) return error;

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
    public ResponseEntity<?> login(@RequestBody UserRequestDTO utilisateur) {

        Utilisateur existingUser =
                userService.getUserByEmail(utilisateur.getEmail());

        if (existingUser == null ||

                !passwordEncoder.matches(utilisateur.getMotDePasse(), existingUser.getMotDePasse())) {

            return ResponseEntity.status(401)
                    .body("Invalid email or password");
        }

        UserResponseDTO response = new UserResponseDTO();
        response.setNom(existingUser.getNom());
        response.setPrenom(existingUser.getPrenom());
        response.setEmail(existingUser.getEmail());
        response.setImage(existingUser.getImage());
        response.setRole(existingUser.getRole());


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private ResponseEntity<?> checkSignup(UserRequestDTO dto) {
        if (userService.getUserByEmail(dto.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already exists"));
        }
        if (!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Passwords do not match"));
        }
        return null;
    }
}