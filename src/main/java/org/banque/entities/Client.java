package org.banque.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue
    private Long code;
    private String nom;
    private String email;
    @OneToMany(mappedBy = "client")
    private Collection<Compte> comptes;

    public Client() {
    }

    public Client(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setComptes(Collection<Compte> comptes) {
        this.comptes = comptes;
    }

    public Long getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public Collection<Compte> getComptes() {
        return comptes;
    }


}
