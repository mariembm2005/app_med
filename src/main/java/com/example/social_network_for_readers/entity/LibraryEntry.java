package com.example.social_network_for_readers.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="library_entry")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateAdded;
    private Format format;
    private String title;

    private String image;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "user_username")
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;
    @ManyToMany
            @JoinTable(name="le_tag",
                    joinColumns = @JoinColumn(name = "libraryEntry_id"),
                    inverseJoinColumns = @JoinColumn(name = "tag_id")
            )
    private Set<Tag> tags = new HashSet<>();
    @ManyToMany
    @JoinTable(name="le_cols",
            joinColumns = @JoinColumn(name = "libraryEntry_id"),
            inverseJoinColumns = @JoinColumn(name = "collection_id")
    )
    private Set<Collection> collections = new HashSet<>();

    public LibraryEntry(Work work, Format format) {
    }




}
