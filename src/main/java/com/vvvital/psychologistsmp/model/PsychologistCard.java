package com.vvvital.psychologistsmp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "card")
public class PsychologistCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Integer price;
    @Column
    private Integer rating;
    @Column
    private Integer experience;
    @Column
    private String specialization;
    @Column
    private String description;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Categories> categories;

    public PsychologistCard(Integer price, Integer rating, Integer experience, String specialization, String description,Set<Categories> categories) {
        this.price = price;
        this.rating = rating;
        this.experience = experience;
        this.specialization = specialization;
        this.description = description;
        this.categories = categories;
    }

    public PsychologistCard(){}

    public PsychologistCard(PsychologistCard model) {
    }

    public Long getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getExperience() {
        LocalDate today = LocalDate.now();
        int startYear = Integer.parseInt(String.valueOf(experience));
        if (startYear > today.getYear()) {
            throw new IllegalArgumentException("The start year must not be greater than the current year");
        }
        return today.getYear() - startYear;
    }

    public String getDescription() {
        return description;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Set<Categories> getCategories() {
        return categories;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "PsychologistCard{" +
                "id=" + id +
                ", price=" + price +
                ", rating=" + rating +
                ", experience=" + experience +
                ", description='" + description + '\'' +
                ", categories " + categories.toString() +
                '}';
    }
}
