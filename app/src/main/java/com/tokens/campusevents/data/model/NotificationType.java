package com.tokens.campusevents.data.model;

public enum NotificationType {
    UPDATE("UPDATE"), REMINDER("REMINDER"), CANCELLED("CANCELLED");

    private final String displayName;
    NotificationType(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
