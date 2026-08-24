package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.ReviewRequestDTO;
import com.example.social_network_for_readers.dto.ReviewResponseDTO;
import com.example.social_network_for_readers.entity.Review;

public class Reviewmapper {
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
        return reviewResponseDTO;
    }
}
