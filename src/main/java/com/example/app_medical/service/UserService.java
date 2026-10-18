package com.example.app_medical.service;

import com.example.app_medical.entity.*;
import com.example.app_medical.repository.*;
import jakarta.transaction.Transactional;
//import org.springframework.security.core.userdetails.*;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Optional;

@Service
@Getter
public class UserService{


private UserRepository userRepository;
public UserService(UserRepository userRepository){
    this.userRepository=userRepository;
}






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




}


