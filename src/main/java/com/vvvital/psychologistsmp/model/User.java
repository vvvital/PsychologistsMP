package com.vvvital.psychologistsmp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_seq")
    @Column
    private Long id;
    @NotBlank
    private String email;
    private String password;
    @Column
    private String firstName;
    @Column
    private String lastName;
    private String photoLink;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Enumerated(EnumType.STRING)
    private Location location;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "card_id")
    private PsychologistCard card;

    public User(String email, String password, String firstName, String lastName, Set<Role> roles, Location location) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.location = location;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Location getLocation() {
        return location;
    }

    public PsychologistCard getCard(){return card;}

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRoles(Set<Role> role) {
        this.roles = role;
    }

    public void setRole(Role role){
        this.roles.add(role);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setCard(PsychologistCard card){this.card=card;}

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                ", location=" + location +
                ", card=" + card +
                '}';
    }
}
