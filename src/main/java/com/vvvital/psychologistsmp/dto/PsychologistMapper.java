package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.Psychologist;
import com.vvvital.psychologistsmp.model.PsychologistCard;
import com.vvvital.psychologistsmp.model.User;

public class PsychologistMapper {

    public static Psychologist userToPsychologist(User user,PsychologistCard card) {
        Psychologist psychologist = new Psychologist();
        psychologist.setId(user.getId());
        psychologist.setEmail(user.getEmail());
        psychologist.setPassword(user.getPassword());
        psychologist.setFirstName(user.getFirstName());
        psychologist.setLastName(user.getLastName());
        psychologist.setRole(user.getRole());
        psychologist.setLocation(user.getLocation());
        psychologist.setCard(card);
        return psychologist;
    }
}
