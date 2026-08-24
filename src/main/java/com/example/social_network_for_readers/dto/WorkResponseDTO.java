package com.example.social_network_for_readers.dto;



import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkResponseDTO {
    private Long id;
    private String title;
    private int originalPubYear;
    private String image;
    private List<AuthorResponseDTO> authorResponseDTOList;
}
