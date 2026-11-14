package com.example.app_medical.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateReservation;

    private boolean confirme;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "creneau_id", nullable = true)
    private Creneau creneau;

    public boolean estConfirmé(){
        return confirme;
    }
    public boolean creneauValide(){
        return this.creneau.getDisponible();
    }

}
