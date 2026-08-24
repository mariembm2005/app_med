package com.example.social_network_for_readers.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private String text;
    private LocalDate dateAdded;
    private float rating;
}
