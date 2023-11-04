package com.vvvital.psychologistsmp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.management.ConstructorParameters;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private Location location;
}
