package com.example.social_network_for_readers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagRequestDTO {

    private Long id;
    @NotBlank(message = "tag name required!!!")
    private String name;
}
