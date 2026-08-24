package com.example.social_network_for_readers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionRequestDTO {
    private Long id;
    @NotBlank(message = "collection name required!!!")
    private String name;
}
