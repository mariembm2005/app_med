package com.example.social_network_for_readers.service;

import com.example.social_network_for_readers.entity.Review;
import com.example.social_network_for_readers.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewService {
    private ReviewRepository reviewRepository;
    public boolean edit(Long id, Review rev) {

        Optional<Review> revi = reviewRepository.findById(id);
        if (revi.isPresent()) {
            Review review = revi.get();
            review.setText(rev.getText());
            review.setRating(rev.getRating());
            review.setDateCreated(rev.getDateCreated());
            reviewRepository.saveAndFlush(review);
            return true;
        }
return false;

        }




        public List<Review> getAllReviews() {
            return reviewRepository.findAll();
        }

        public Review getReview(Long id) {
            Optional<Review> review = reviewRepository.findById(id);

            if (review.isPresent()) {
                return review.get();
            }

            return null;
        }

        public Review addReview(Review review) {
            return reviewRepository.saveAndFlush(review);
        }

        public Review updateReview(Long id, Review updatedReview) {

            Optional<Review> review = reviewRepository.findById(id);

            if (review.isPresent()) {

                Review r = review.get();

                r.setText(updatedReview.getText());
                r.setDateCreated(updatedReview.getDateCreated());
                r.setRating(updatedReview.getRating());
                r.setWork(updatedReview.getWork());
                r.setUser(updatedReview.getUser());

                return reviewRepository.saveAndFlush(r);
            }

            return null;
        }

        public void deleteReview(Long id) {

            Optional<Review> review = reviewRepository.findById(id);

            if (review.isPresent()) {

                Review r = review.get();

                reviewRepository.delete(r);
                reviewRepository.flush();
            }
        }
    }


