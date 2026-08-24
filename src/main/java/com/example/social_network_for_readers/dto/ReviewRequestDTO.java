package com.example.social_network_for_readers.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDTO {
    private Long id;
    @NotBlank(message = "text required!!!")
    private String text;
    @CreatedDate
    private LocalDate dateAdded;
    @PositiveOrZero
    private float rating;

}
