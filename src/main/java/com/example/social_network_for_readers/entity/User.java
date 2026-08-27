package com.example.social_network_for_readers.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Entity
@Table(name="users")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String image;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LibraryEntry> entries = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Collection> collections = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();
    @ManyToMany(mappedBy = "users")
    @JsonIgnore

    private Set<Group> groups = new HashSet<>();
    public User(String username, String email, String password,String confirmPassword, String image) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.image = image;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(this==null || obj==null)
            return false;
        if(obj instanceof User u){
            return this.username.equals(u.username);
        }
        return false;

    }



}
