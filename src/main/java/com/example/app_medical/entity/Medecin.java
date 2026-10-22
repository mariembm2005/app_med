package com.example.app_medical.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Medecin extends Utilisateur {

    @Column(unique = true, nullable = false)
    private String numeroOrdre;

    @ManyToOne
    @JoinColumn(name = "specialite_id", nullable = false)
    private Specialite specialite;
    @Column(nullable = false)
    private Boolean approved = false;

    public boolean isApproved() {
        return Boolean.TRUE.equals(approved);
    }

    public void soumettreDemandeInscription() {
        this.approved = true;
    }
}
