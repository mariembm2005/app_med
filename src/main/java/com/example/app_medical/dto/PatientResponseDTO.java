package com.example.app_medical.dto;

import com.example.app_medical.entity.SexeEnum;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PatientResponseDTO extends UserResponseDTO {

    private String telephone;


}