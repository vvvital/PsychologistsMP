package com.vvvital.psychologistsmp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card")
public class PsychologistCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    //    private Long userId;
    @Column
    private Integer price;
    @Column
    private Integer rating;
    @Column
    private Integer experience;
    @Column
    private String description;
    @Column
    private String photoLink;
//    @Enumerated(EnumType.STRING)
//    private Set<Categories> categories;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public PsychologistCard(Integer price, Integer rating, Integer experience, String description, String photoLink) {
        this.price = price;
        this.rating = rating;
        this.experience = experience;
        this.description = description;
        this.photoLink = photoLink;
//        this.categories = categories;
    }

    @OneToOne(mappedBy = "card", optional = false)
    private Psychologist card_id;

    public Psychologist getCard_id() {
        return card_id;
    }

    public void setCard_id(Psychologist card_id) {
        this.card_id = card_id;
    }
}
