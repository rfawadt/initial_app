package com.tokens.campusevents.data.model;

public class EventUpdate {
    public String id;
    public String eventId;
    public String message;
    public String timestamp;
    public UpdateType type;

    public EventUpdate(String id, String eventId, String message, String timestamp, UpdateType type) {
        this.id = id;
        this.eventId = eventId;
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
    }
}
