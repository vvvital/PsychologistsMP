package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.Categories;
import com.vvvital.psychologistsmp.model.PsychologistCard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PsychologistCardDTO {
    private Integer price;
    private Integer rating;
    private Integer experience;
    private String description;
    private String photoLink;
    private Set<Categories> categories;

    public static PsychologistCard toModel(PsychologistCardDTO dto) {
        PsychologistCard card = new PsychologistCard();
        card.setPrice(dto.getPrice());
        card.setRating(dto.getRating());
        card.setExperience(dto.getExperience());
        card.setDescription(dto.getDescription());
        card.setPhotoLink(dto.getPhotoLink());
        card.setCategories(dto.getCategories());
        return card;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getExperience() {
        return experience;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public Set<Categories> getCategories() {
        return categories;
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

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories=categories;
    }
}


//{
//        "price":"100",
//        "rating":"5",
//        "experience":"15",
//        "description":"description",
//        "photoLink":"link",
//        "categories":["CLINICAL_PSYCHOLOGIST","CHILD_PSYCHOLOGIST","FAMILY_PSYCHOLOGIST"]
//        }