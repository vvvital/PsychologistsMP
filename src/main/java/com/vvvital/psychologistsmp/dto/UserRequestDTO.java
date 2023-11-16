package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.Location;
import com.vvvital.psychologistsmp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private Location location;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Role> getRoles() {
        return roles ;
    }

    public void setRole(Role...role) {
        System.out.println("UserRequestDTO.setRole");
        Stream.of(role).forEach(System.out::println);
        this.roles= Stream.of(role).collect(Collectors.toSet());
        System.out.println("set from method setRole" + this.roles);

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
