package com.example.social_network_for_readers.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="work")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int originalPubYear;
    private String image;
    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL)
private List<Review> reviews;
    @ManyToMany
    @JoinTable(
            name = "work_author",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )

    private List<Author> authors = new ArrayList<>();
    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL)
    private List<LibraryEntry> libraryEntries = new ArrayList<>();



    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(this==null)
            return false;
        if(this instanceof Work w){
            return this.id.equals(w.id);
        }
        return false;
    }


}
