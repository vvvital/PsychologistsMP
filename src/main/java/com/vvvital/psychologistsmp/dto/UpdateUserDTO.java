package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.Location;

public class UpdateUserDTO {

    private String firstName;
    private String lastName;
    private Location location;


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


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
