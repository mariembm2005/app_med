package com.example.social_network_for_readers.dto;

import com.example.social_network_for_readers.entity.Work;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkRequestDTO {
    private Long id;
    @NotBlank(message = "work title required!!!")
    private String title;
    @PositiveOrZero
    private int originalPubYear;
    @NotBlank(message="work image required!!!")
    private String image;
    private List<AuthorRequestDTO> authorRequestDTOList;
}
