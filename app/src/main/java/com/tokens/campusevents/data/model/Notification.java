package com.tokens.campusevents.data.model;

public class Notification {
    public String id;
    public String eventId;
    public String eventName;
    public String message;
    public String timeAgo;
    public NotificationType type;

    public Notification(String id, String eventId, String eventName, String message,
                        String timeAgo, NotificationType type) {
        this.id = id;
        this.eventId = eventId;
        this.eventName = eventName;
        this.message = message;
        this.timeAgo = timeAgo;
        this.type = type;
    }
}
