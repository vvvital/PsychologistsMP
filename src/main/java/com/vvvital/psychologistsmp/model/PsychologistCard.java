package com.vvvital.psychologistsmp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PsychologistCard {
    private Integer price;
    private Integer rating;
    private Integer experience;
    private String description;
    private String photoLink;
    private Categories categories;
}
