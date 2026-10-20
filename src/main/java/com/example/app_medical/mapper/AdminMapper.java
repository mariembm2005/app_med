package com.example.app_medical.mapper;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import org.springframework.stereotype.*;

@Component
public class AdminMapper extends UserMapper {

    public Administrateur toAdmin(AdminRequestDTO dto) {
        Administrateur admin = new Administrateur();
        updateEntity(admin, dto);
        admin.setRole(RoleEnum.ADMIN);
        return admin;
    }

    public AdminResponseDTO toAdminResponse(Administrateur admin) {
        AdminResponseDTO dto = new AdminResponseDTO();
        fillResponse(dto, admin);
        return dto;
    }
    public Administrateur toEntity(AdminRequestDTO dto){
        Administrateur admin = new Administrateur();
        updateEntity(admin, dto);
        admin.setRole(RoleEnum.ADMIN);
        return admin;

    }
}