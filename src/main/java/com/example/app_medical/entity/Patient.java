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


    public void sInscrire() {
    }
}
