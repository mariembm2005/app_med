package com.example.social_network_for_readers.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDTO {
    private Long id;
    private String name;
    private String description;
}
