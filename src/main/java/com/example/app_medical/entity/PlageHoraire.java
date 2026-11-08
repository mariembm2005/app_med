package com.example.app_medical.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlageHoraire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @OneToMany(mappedBy = "plageHoraire",
            cascade = CascadeType.ALL)
    private List<Creneau> creneaux;

    public List<Creneau> generateCreneaux(){
        List<Creneau> creneau = new ArrayList<>();
        return creneau;
    }
}