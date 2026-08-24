package com.example.social_network_for_readers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDTO {

    private Long id;
    private String name;
}
