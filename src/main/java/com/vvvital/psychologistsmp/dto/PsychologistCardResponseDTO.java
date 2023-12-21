package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.Categories;
import com.vvvital.psychologistsmp.model.PsychologistCard;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PsychologistCardResponseDTO {

    private Integer price;
    private Integer rating;
    private Integer experience;
    private String description;
    private String specialization;
    private String photoLink;
    private Set<Categories> categories;

    public Set<Categories>getCategories(){
        return categories;
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

    public String getSpecialization() {
        return specialization;
    }

    public String getPhotoLink() {
        return photoLink;
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

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }

    public static PsychologistCardResponseDTO toDTO(PsychologistCard card){
        PsychologistCardResponseDTO dto=new PsychologistCardResponseDTO();
        dto.setPrice(card.getPrice());
        dto.setRating(card.getRating());
        dto.setExperience(card.getExperience());
        dto.setDescription(card.getDescription());
        dto.setSpecialization(card.getSpecialization());
        dto.setPhotoLink(card.getPhotoLink());
        dto.setCategories(card.getCategories());

        return dto;
    }
}
