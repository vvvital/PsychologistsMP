package com.vvvital.psychologistsmp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Psychologist extends User {

    private PsychologistCard card;

    public Psychologist(String email, String password, String firstName, String lastName, Role role, Location location, PsychologistCard card) {
        super(email, password, firstName, lastName, role, location);
        this.card = card;
    }
}
