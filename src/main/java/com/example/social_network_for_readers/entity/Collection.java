package com.example.social_network_for_readers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name="collection")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY) // LAZY is best practice for performance
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(mappedBy = "collections")
    private List<LibraryEntry> libraryEntries = new ArrayList<>();
public Collection(Long id, String name){}

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(obj==null)
            return false;
        if(this instanceof Collection c){
            return this.name.equals(c.name) && this.id==c.id;
        }
        return false;
    }
}
