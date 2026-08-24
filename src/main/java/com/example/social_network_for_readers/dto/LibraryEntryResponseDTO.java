package com.example.social_network_for_readers.dto;

import com.example.social_network_for_readers.entity.Format;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryEntryResponseDTO {
    private Long id;
    private LocalDate dateAdded;
    private Format format;
    private String image;
    private float rating;
    private List<CollectionResponseDTO> collectionResponseDTOList;
    private List<TagResponseDTO> tagResponseDTOList;
    private WorkResponseDTO workResponseDTOList;
}
