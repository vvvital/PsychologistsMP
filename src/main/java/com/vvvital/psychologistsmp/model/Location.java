package com.vvvital.psychologistsmp.model;

public enum Location {
    KYIV ("Київ"),
    ODESSA ("Одесса"),
    DNIPRO ("Дніпро"),
    LVIV ("Львів");

    private final String displayLocation;

    Location(String displayLocation) {
        this.displayLocation = displayLocation;
    }

    @Override
    public String toString() {
        return displayLocation;
    }
}
