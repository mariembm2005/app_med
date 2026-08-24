package com.example.social_network_for_readers.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String username;
    private String email;
    private String image;
    private List<LibraryEntryResponseDTO> libraryEntryResponseDTOList;
    private List<ReviewResponseDTO> reviewResponseDTOList;
    private List<GroupResponseDTO> groupResponseDTOList;
    private List<CollectionResponseDTO> collectionResponseDTOList;
}