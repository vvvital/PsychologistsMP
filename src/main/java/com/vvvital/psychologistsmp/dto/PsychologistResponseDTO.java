package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class PsychologistResponseDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private Location location;
    private PsychologistCard card;
    public static PsychologistResponseDTO toDTO(User psychologist){
        PsychologistResponseDTO dto=new PsychologistResponseDTO();
        dto.setId(psychologist.getId());
        dto.setEmail(psychologist.getEmail());
        dto.setFirstName(psychologist.getFirstName());
        dto.setLastName(psychologist.getLastName());
        dto.setLocation(psychologist.getLocation());
        dto.setRole(psychologist.getRoles());
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

    public void setRole(Set<Role> roles) {
        this.roles = roles;
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

    public Set<Role> getRole() {
        return roles;
    }

    public String getLocation() {
        return location.toString();
    }

    public PsychologistCardResponseDTO getCard() { return PsychologistCardResponseDTO.toDTO(card);
    }
}
