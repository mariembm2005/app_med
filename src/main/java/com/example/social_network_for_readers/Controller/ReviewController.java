package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.dto.ReviewRequestDTO;
import com.example.social_network_for_readers.dto.ReviewResponseDTO;
import com.example.social_network_for_readers.entity.Review;
import com.example.social_network_for_readers.mapper.Reviewmapper;
import com.example.social_network_for_readers.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final Reviewmapper reviewmapper;

    @GetMapping
    public List<ReviewResponseDTO> getAllRevs() {
        return reviewmapper.toReviewResponseDTO(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ReviewResponseDTO getRev(@PathVariable Long id) {
        return reviewmapper.toReviewResponseDTO(reviewService.getReview(id));
    }

    @PostMapping
    public ReviewResponseDTO addRev(@RequestBody ReviewRequestDTO review) {
        return reviewmapper.toReviewResponseDTO(reviewService.addReview(reviewmapper.toEntity(review)));
    }

    @PutMapping("/{id}")
    public ReviewResponseDTO updateRev(
            @PathVariable Long id,
            @RequestBody ReviewRequestDTO review) {

        return reviewmapper.toReviewResponseDTO(reviewService.updateReview(id, reviewmapper.toEntity(review)));
    }

    @DeleteMapping("/{id}")
    public void deleteRev(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
