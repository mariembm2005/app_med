package com.example.social_network_for_readers.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Enumerated(EnumType.STRING)
    private Format format;
    private String title;

    private String image;
    private float rating;
    @ManyToOne
    @JoinColumn(name = "user_id")
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
    @JsonIgnore
    private Set<Collection> collections = new HashSet<>();

    public LibraryEntry(Work work, Format format) {
    }




}
