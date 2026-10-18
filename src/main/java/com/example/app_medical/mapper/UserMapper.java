package com.example.app_medical.mapper;

import com.example.app_medical.dto.UserRequestDTO;
import com.example.app_medical.dto.UserResponseDTO;
import com.example.app_medical.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public Utilisateur toEntity(UserRequestDTO dto) {
        Utilisateur user;
        RoleEnum role = dto.getRole() != null ? dto.getRole() : RoleEnum.PATIENT;


        switch (role) {
            case PATIENT:
                user = new Patient();
                break;
            case MEDECIN:
                user = new Medecin();
                break;
            case ADMIN:
                user = new Administrateur();
                break;
            default:
                throw new IllegalArgumentException("Rôle invalide");
        }

        updateEntity(user, dto);
        return user;
    }


    public UserResponseDTO toResponseDto(Utilisateur userEntity) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setNom(userEntity.getNom());
        userResponseDTO.setPrenom(userEntity.getPrenom());
        userResponseDTO.setEmail(userEntity.getEmail());
        userResponseDTO.setImage(userEntity.getImage());
        userResponseDTO.setRole(userEntity.getRole());
        return userResponseDTO;
    }


    private void updateEntity(Utilisateur userEntitytmp, UserRequestDTO userRequestDTO) {
        userEntitytmp.setEmail(userRequestDTO.getEmail());
        userEntitytmp.setMotDePasse(userRequestDTO.getMotDePasse());
        userEntitytmp.setNom(userRequestDTO.getNom());
        userEntitytmp.setPrenom(userRequestDTO.getPrenom());
        userEntitytmp.setRole(userRequestDTO.getRole());
        userEntitytmp.setImage(userRequestDTO.getImage());
        userEntitytmp.setConfirmMotDePasse(userRequestDTO.getConfirmMotDePasse());
        userEntitytmp.setRole(userRequestDTO.getRole());

    }
    public List<UserResponseDTO> toResponseDtoLis(List<Utilisateur> users) {
        return users.stream()
                .map(this::toResponseDto)
                .toList();
    }

}
