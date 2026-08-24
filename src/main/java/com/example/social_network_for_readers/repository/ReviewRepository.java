package com.example.social_network_for_readers.repository;

import com.example.social_network_for_readers.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
