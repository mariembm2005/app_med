package com.example.app_medical.entity;


import jakarta.persistence.*;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    private String motDePasse;
    private String confirmMotDePasse;
    private String image;

    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.PATIENT;

    public String seConnecter() {
        return "Connexion";
    }

    public void seDeconnecter() {
    }
}