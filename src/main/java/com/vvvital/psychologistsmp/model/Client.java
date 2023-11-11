package com.vvvital.psychologistsmp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Client extends User{

    public Client(String email, String password, String firstName, String lastName, Role role, Location location) {
        super(email, password, firstName, lastName, role, location);
    }
    public Client(){}

}
