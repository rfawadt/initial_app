package com.tokens.campusevents.data.model;

public class Rsvp {
    public String userId;
    public String userName;
    public String userEmail;
    public String eventId;
    public RsvpStatus status;

    public Rsvp(String userId, String userName, String userEmail, String eventId, RsvpStatus status) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.eventId = eventId;
        this.status = status;
    }
}
