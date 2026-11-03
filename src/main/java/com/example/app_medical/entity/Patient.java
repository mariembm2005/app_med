package com.example.app_medical.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patient extends Utilisateur {



    private String telephone;
    @Enumerated(EnumType.STRING)
    private SexeEnum sexe;
    private java.time.LocalDate dateNaissance;

    public void sInscrire() {
    }
}
