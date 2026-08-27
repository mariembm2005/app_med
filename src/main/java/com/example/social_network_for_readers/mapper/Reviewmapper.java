package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.*;
import com.example.social_network_for_readers.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Reviewmapper {
    private final WorkMapper workMapper;
    private final UserMapper userMapper;

    public Review toEntity(ReviewRequestDTO reviewRequestDTO) {
        Review review = new Review();
        review.setId(reviewRequestDTO.getId());
        review.setText(reviewRequestDTO.getText());
        review.setRating(reviewRequestDTO.getRating());
        review.setDateCreated(reviewRequestDTO.getDateAdded());
        return review;
    }
    public ReviewResponseDTO toReviewResponseDTO(Review review) {
        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
        reviewResponseDTO.setId(review.getId());
        reviewResponseDTO.setText(review.getText());
        reviewResponseDTO.setRating(review.getRating());
        reviewResponseDTO.setDateAdded(review.getDateCreated());
        User user = review.getUser();
        Work work = review.getWork();
        reviewResponseDTO.setWork(workMapper.toDTO(work));
        reviewResponseDTO.setUser(userMapper.toResponseDto(user));
        return reviewResponseDTO;
    }
    public List<ReviewResponseDTO> toReviewResponseDTO(List<Review> reviews) {
        return reviews.stream()
                .map(this::toReviewResponseDTO)
                .collect(Collectors.<ReviewResponseDTO>toList());
    }
}
