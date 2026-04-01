package com.tokens.campusevents.data.model;

public enum UpdateType {
    GENERAL("General"), VENUE_CHANGE("Venue Change"), TIME_CHANGE("Time Change"), CANCELLATION("Cancellation");

    private final String displayName;
    UpdateType(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
