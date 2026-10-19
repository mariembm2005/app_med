package com.example.app_medical.Controller;

import com.example.app_medical.dto.UserRequestDTO;
import com.example.app_medical.dto.UserResponseDTO;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.UserMapper;
import com.example.app_medical.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequestDTO utilisateur) {


        if (userService.getUserByEmail(utilisateur.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if (!utilisateur.getMotDePasse().equals(utilisateur.getConfirmMotDePasse())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
        Utilisateur user = userMapper.toEntity(utilisateur);

        user.setMotDePasse(
                passwordEncoder.encode(utilisateur.getMotDePasse())
        );
        user.setConfirmMotDePasse(
                passwordEncoder.encode(utilisateur.getConfirmMotDePasse())

        );

        user.setNom(utilisateur.getNom());
        user.setPrenom(utilisateur.getPrenom());
        user.setEmail(utilisateur.getEmail());
        user.setImage(utilisateur.getImage());
        user.setRole(utilisateur.getRole());
        Utilisateur saved = userService.createUser(user);

        UserResponseDTO response = new UserResponseDTO(
                saved.getNom(),
                saved.getPrenom(),
                saved.getEmail(),
                saved.getImage(),
                saved.getRole()

        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO utilisateur) {

        Utilisateur existingUser =
                userService.getUserByEmail(utilisateur.getEmail());

        if (existingUser == null ||
                !passwordEncoder.matches(
                        utilisateur.getMotDePasse(),
                        existingUser.getMotDePasse())) {

            return ResponseEntity.status(401)
                    .body("Invalid email or password");
        }

        return ResponseEntity.ok("Login successful");
    }
}