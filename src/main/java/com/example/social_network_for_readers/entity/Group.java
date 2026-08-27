package com.example.social_network_for_readers.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Table(name="groupp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "group_user",
            joinColumns = @JoinColumn(name = "grp_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();





}
