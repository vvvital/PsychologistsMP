package com.vvvital.psychologistsmp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class PsychologistCard {
    private Integer price;
    private Integer rating;
    private Integer experience;
    private String description;
    private String photoLink;
    private Set<Categories> categories;
}
