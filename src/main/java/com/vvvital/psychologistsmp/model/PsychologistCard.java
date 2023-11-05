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
    @SequenceGenerator(name = "id_seq", sequenceName = "card_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    //    private Long userId;
    @Column(name = "price")
    private Integer price;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "description")
    private String description;
    @Column(name = "photoLink")
    private String photoLink;
//    @Enumerated(EnumType.STRING)
//    private Set<Categories> categories;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Psychologist psychologist;

    public PsychologistCard(Integer price, Integer rating, Integer experience, String description, String photoLink) {
        this.price = price;
        this.rating = rating;
        this.experience = experience;
        this.description = description;
        this.photoLink = photoLink;
//        this.categories = categories;
    }
}
