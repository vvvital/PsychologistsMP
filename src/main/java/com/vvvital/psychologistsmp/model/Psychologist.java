package com.vvvital.psychologistsmp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Getter
//@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Psychologist extends User {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "card_id")
    private PsychologistCard card;

    public Psychologist(String email, String password, String firstName, String lastName, Role role, Location location, PsychologistCard card) {
        super(email, password, firstName, lastName, role, location);
        this.card = card;
    }

    public Psychologist(){}

    public void setCard(PsychologistCard card){
        this.card=card;
    }

    public PsychologistCard getCard() {
        return card;
    }



    //
//
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + super.getId() +
//                ", email='" + super.getEmail() + '\'' +
//                ", password='" + super.getPassword() + '\'' +
//                ", firstName='" + super.getFirstName() + '\'' +
//                ", lastName='" + super.getLastName() + '\'' +
//                ", role=" + super.getRole() +
//                ", location=" + super.getLocation() +
//                '}'+ '\''+
//                "   PsychologistCard{" +
//                "id=" + card.getId() +
//                ", price=" + card.getPrice() +
//                ", rating=" + card.getRating() +
//                ", experience=" + card.getExperience() +
//                ", description='" + card.getDescription() + '\'' +
//                ", photoLink='" + card.getPhotoLink() + '\'' +
//                ", categories " + card.getCategories().toString() +
//                '}';
//    }
}
