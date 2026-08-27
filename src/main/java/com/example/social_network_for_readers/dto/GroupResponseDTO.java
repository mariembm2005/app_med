package com.example.social_network_for_readers.dto;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDTO {
    private Long id;
    private String name;
    private String description;
    private List<UserResponseDTO> members;
}
