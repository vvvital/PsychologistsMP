package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.Location;
import com.vvvital.psychologistsmp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Location location;

}