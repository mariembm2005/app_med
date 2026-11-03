package com.example.app_medical.mapper;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import org.springframework.stereotype.*;

@Component
public class PatientMapper extends UserMapper {

    public Patient toPatient(PatientRequestDTO dto) {
        Patient patient = new Patient();
        updateEntity(patient, dto);          // inherited
        patient.setRole(RoleEnum.PATIENT);

        patient.setSexe(dto.getSexe());
        patient.setDateNaissance(dto.getDateNaissance());
        patient.setTelephone(dto.getTelephone());
        return patient;
    }

    public PatientResponseDTO toPatientResponse(Patient patient) {
        PatientResponseDTO dto = new PatientResponseDTO();
        fillResponse(dto, patient);

        dto.setTelephone(patient.getTelephone());
        dto.setDateNaissance(patient.getDateNaissance());
        dto.setSexe(patient.getSexe());
        dto.setDateNaissance(patient.getDateNaissance());
        return dto;
    }

    public Patient toEntity(PatientRequestDTO dto) {
        Patient patient = new Patient();
        updateEntity(patient, dto);
        patient.setRole(dto.getRole());
        patient.setSexe(dto.getSexe());
        patient.setDateNaissance(dto.getDateNaissance());
        patient.setTelephone(dto.getTelephone());
        return patient;
    }
}