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
        patient.setAge(dto.getAge());
        patient.setTelephone(dto.getTelephone());
        return patient;
    }

    public PatientResponseDTO toPatientResponse(Patient patient) {
        PatientResponseDTO dto = new PatientResponseDTO();
        fillResponse(dto, patient);
        dto.setAge(patient.getAge());
        dto.setTelephone(patient.getTelephone());
        return dto;
    }

    public Patient toEntity(PatientRequestDTO dto) {
        Patient patient = new Patient();
        updateEntity(patient, dto);
        patient.setRole(dto.getRole());
        patient.setAge(dto.getAge());
        patient.setTelephone(dto.getTelephone());
        return patient;
    }
}