package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.*;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class PsychologistResponseDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Location location;
    private PsychologistCard card;
    public static PsychologistResponseDTO toDTO(Psychologist psychologist){
        PsychologistResponseDTO dto=new PsychologistResponseDTO();
        dto.setId(psychologist.getId());
        dto.setEmail(psychologist.getEmail());
        dto.setFirstName(psychologist.getFirstName());
        dto.setLastName(psychologist.getLastName());
        dto.setLocation(psychologist.getLocation());
        dto.setRole(psychologist.getRole());
        dto.setCard(psychologist.getCard());
        return dto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setCard(PsychologistCard card) {
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public Location getLocation() {
        return location;
    }

    public PsychologistCard getCard() {
        return card;
    }
}
