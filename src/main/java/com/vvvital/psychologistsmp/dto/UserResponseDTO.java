package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.Location;
import com.vvvital.psychologistsmp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

public class UserResponseDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String photoLink;
    private Set<Role>roles;
    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public Set<Role> getRole() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setRole(Role role){roles.add(role);}

    public String getLocation() {
        return location.toString();
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
