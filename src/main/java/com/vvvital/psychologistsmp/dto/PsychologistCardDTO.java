package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.Categories;
import com.vvvital.psychologistsmp.model.PsychologistCard;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PsychologistCardDTO {
    private Integer price;
    private Integer rating;
    private Integer experience;
    private String description;
    private String specialization;
    private String photoLink;
    private Set<Categories> categories;

    public static PsychologistCard toModel(PsychologistCardDTO dto) {
        PsychologistCard card = new PsychologistCard();
        card.setPrice(dto.getPrice());
        card.setRating(dto.getRating());
        card.setExperience(dto.getExperience());
        card.setDescription(dto.getDescription());
        card.setSpecialization(dto.getSpecialization());
        card.setPhotoLink(dto.getPhotoLink());
        card.setCategories(dto.getCategories());
        return card;
    }

    public static PsychologistCardDTO toDTO(PsychologistCard model) {
        PsychologistCardDTO dto = new PsychologistCardDTO();
        dto.setPrice(model.getPrice());
        dto.setRating(model.getRating());
        dto.setExperience(model.getExperience());
        dto.setDescription(model.getDescription());
        dto.setSpecialization(model.getSpecialization());
        dto.setPhotoLink(model.getPhotoLink());
        dto.setCategories(model.getCategories());
        return dto;
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

    public String getPhotoLink() {
        return photoLink;
    }

    public Set<Categories> getCategories() {
        return categories;
    }

    public String getSpecialization() {
        return specialization;
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

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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