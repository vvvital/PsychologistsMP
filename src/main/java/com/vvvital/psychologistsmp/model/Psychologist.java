package com.vvvital.psychologistsmp.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Psychologist extends User{

    private PsychologistCard psychologistCard;

    public Psychologist(String email, String password, String firstName, String lastName, Role role, Location location, PsychologistCard psychologistCard) {
        super(email, password, firstName, lastName, role, location);
        this.psychologistCard = psychologistCard;
    }
}
