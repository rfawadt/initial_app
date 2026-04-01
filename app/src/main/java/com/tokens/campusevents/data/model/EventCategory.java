package com.tokens.campusevents.data.model;

public enum EventCategory {
    ALL("All"), SOCIETY("Society"), TECH("Tech"), ARTS("Arts"),
    BUSINESS("Business"), SPORTS("Sports"), ACADEMIC("Academic");

    private final String displayName;
    EventCategory(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
