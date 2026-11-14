package com.example.app_medical.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;

    @OneToMany(
            mappedBy = "plageHoraire",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Creneau> creneaux = new ArrayList<>();
    public List<Creneau> generateCreneaux(Integer dureeCreneau) {
        if (dureeCreneau == null || dureeCreneau <= 0) {
            throw new IllegalArgumentException(
                    "La durée doit être supérieure à zéro."
            );
        }

        if (heureDebut == null || heureFin == null) {
            throw new IllegalStateException(
                    "Les heures de début et de fin sont obligatoires."
            );
        }

        if (!heureFin.isAfter(heureDebut)) {
            throw new IllegalArgumentException(
                    "L'heure de fin doit être après l'heure de début."
            );
        }

        List<Creneau> resultat = new ArrayList<>();
        LocalTime debut = heureDebut;

        while (!debut.plusMinutes(dureeCreneau).isAfter(heureFin)) {
            LocalTime fin = debut.plusMinutes(dureeCreneau);

            Creneau creneau = new Creneau();
            creneau.setHeureDebut(debut);
            creneau.setHeureFin(fin);
            creneau.setDisponible(true);
            creneau.setTypeSoin("Consultation");
            creneau.setPlageHoraire(this);

            resultat.add(creneau);
            debut = fin;
        }

        return resultat;
    }
}