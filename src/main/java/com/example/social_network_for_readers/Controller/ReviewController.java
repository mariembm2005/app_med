package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.entity.Review;
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

    @GetMapping
    public List<Review> getAllRevs() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public Review getRev(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    @PostMapping
    public Review addRev(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @PutMapping("/{id}")
    public Review updateRev(
            @PathVariable Long id,
            @RequestBody Review review) {

        return reviewService.updateReview(id, review);
    }

    @DeleteMapping("/{id}")
    public void deleteRev(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
