package com.example.social_network_for_readers.entity;


import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Table(name="review")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private LocalDate dateCreated;
    private float rating;
    @ManyToOne(fetch = FetchType.LAZY) // LAZY is best practice for performance
    @JoinColumn(name = "work_id")
    private Work work;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Review(Work work, String text) {
        this.work = work;
        this.text = text;
        this.dateCreated = LocalDate.now();
    }




}
