package com.example.app_medical.entity;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
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

    private boolean disponible = true;

    private String typeSoin;

    @ManyToOne
    @JoinColumn(name = "plage_horaire_id")
    private PlageHoraire plageHoraire;

    @OneToOne(mappedBy = "creneau")
    private RendezVous rendezVous;

    public boolean estDisponible() {
        return this.disponible;
    }
}
