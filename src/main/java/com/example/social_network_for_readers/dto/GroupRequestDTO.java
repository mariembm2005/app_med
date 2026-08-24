package com.example.social_network_for_readers.dto;


import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequestDTO {

    private Long id;
    @NotBlank(message = "name mustn't be empty")
    private String name;
    private String description;
}
