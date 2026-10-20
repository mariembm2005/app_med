package com.example.app_medical.service;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import jakarta.transaction.Transactional;
//import org.springframework.security.core.userdetails.*;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Optional;

@Service
@Getter
@AllArgsConstructor
public class UserService{


private UserRepository userRepository;
private PatientMapper patientMapper;
private PatientRepository patientRepository;
private MedecinMapper medecinMapper;
private SpecialiteRepository specialiteRepository;
private MedecinRepository medecinRepository;
private PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();








    public List<Utilisateur> getAllUsers() {
        return userRepository.findAll();
    }

    public Utilisateur getUserByID(Long id) {
       Optional<Utilisateur> user = this.userRepository.findById(id);
       return user.orElse(null);

    }


    public Utilisateur getUserByEmail(String email) {
        Optional<Utilisateur> user = this.userRepository.findByEmail(email);
        return user.orElse(null);
    }



    @Transactional
    public Utilisateur updateUser(Long id, Utilisateur updatedUser) {

        Optional<Utilisateur> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return null;
        }

        Utilisateur user = optionalUser.get();


        user.setEmail(updatedUser.getEmail());
        user.setMotDePasse(updatedUser.getMotDePasse());
        user.setConfirmMotDePasse(updatedUser.getConfirmMotDePasse());
        user.setImage(updatedUser.getImage());
        user.setRole(updatedUser.getRole());


        return user;
    }

    public boolean deleteUser(Long id) {
        Optional<Utilisateur> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            userRepository.flush();
            return true;
        }
        return false;

    }
    public Utilisateur createUser(Utilisateur user){
        return this.userRepository.saveAndFlush(user);
    }
    public Patient createPatient(PatientRequestDTO dto) {
        Patient patient = patientMapper.toEntity(dto);
        patient.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        patient.setRole(RoleEnum.PATIENT);
        return patientRepository.save(patient);
    }

    public Medecin createMedecin(MedecinRequestDTO dto) {

        Medecin medecin = medecinMapper.toEntity(dto);

        medecin.setMotDePasse(
                passwordEncoder.encode(dto.getMotDePasse())
        );

        medecin.setRole(RoleEnum.MEDECIN);

        Specialite specialite = specialiteRepository
                .findById(dto.getSpecialiteId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Spécialité introuvable : " + dto.getSpecialiteId()
                        )
                );

        medecin.setSpecialite(specialite);

        return medecinRepository.save(medecin);
    }



}


