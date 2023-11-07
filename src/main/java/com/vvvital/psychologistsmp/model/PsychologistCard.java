package com.vvvital.psychologistsmp.model;

import jakarta.persistence.*;
import lombok.*;

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
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Categories> categories;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private User user;

    public PsychologistCard(Integer price, Integer rating, Integer experience, String description, String photoLink,Set<Categories> categories) {
        this.price = price;
        this.rating = rating;
        this.experience = experience;
        this.description = description;
        this.photoLink = photoLink;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "PsychologistCard{" +
                "id=" + id +
                ", price=" + price +
                ", rating=" + rating +
                ", experience=" + experience +
                ", description='" + description + '\'' +
                ", photoLink='" + photoLink + '\'' +
                ", categories " + categories.toString() +
                '}';
    }
}
