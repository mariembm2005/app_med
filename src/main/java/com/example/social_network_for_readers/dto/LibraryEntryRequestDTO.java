package com.example.social_network_for_readers.dto;

import com.example.social_network_for_readers.entity.Format;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryEntryRequestDTO {


    private Long id;
    @CreatedDate
    @NotBlank(message = "date required!!!")
    private LocalDate dateAdded;
    private Format format;

    private String image;
    @PositiveOrZero
    private float rating;
    private List<CollectionRequestDTO> collectionRequestDTOList;
    private List<TagRequestDTO> tagRequestDTOList;
    private WorkRequestDTO workRequestDTO;
    private UserRequestDTO userRequestDTO;
    private String title;
}
