package com.vvvital.psychologistsmp.dto;
import com.vvvital.psychologistsmp.model.PsychologistCard;
import com.vvvital.psychologistsmp.model.User;

public class PsychologistMapper {

    public static User userToPsychologist(User user,PsychologistCard card) {
        User psychologist = new User();
        psychologist.setId(user.getId());
        psychologist.setEmail(user.getEmail());
        psychologist.setPassword(user.getPassword());
        psychologist.setFirstName(user.getFirstName());
        psychologist.setLastName(user.getLastName());
        psychologist.setRoles(user.getRoles());
        psychologist.setLocation(user.getLocation());
        psychologist.setCard(card);
        return psychologist;
    }
}
