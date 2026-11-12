package com.example.app_medical.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    private Boolean disponible;

    private String typeSoin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plage_horaire_id")
    @JsonIgnore
    private PlageHoraire plageHoraire;
}