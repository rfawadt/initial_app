package com.tokens.campusevents.data.model;

public class Event {
    public String id;
    public String title;
    public String description;
    public String organizer;
    public String organizerId;
    public String venue;
    public String venueHint;
    public String date;
    public String time;
    public String endTime;
    public EventCategory category;
    public int capacity;
    public int currentRsvps;
    public int price;
    public boolean isFree;
    public boolean isOnline;
    public EventStatus status;
    public int imageRes;

    public Event(String id, String title, String description, EventCategory category,
                 String organizer, String organizerId, String venue, String venueHint,
                 String date, String time, String endTime, int capacity, int currentRsvps,
                 int price, boolean isOnline, EventStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.organizer = organizer;
        this.organizerId = organizerId;
        this.venue = venue;
        this.venueHint = venueHint != null ? venueHint : "";
        this.date = date;
        this.time = time;
        this.endTime = endTime != null ? endTime : "";
        this.capacity = capacity;
        this.currentRsvps = currentRsvps;
        this.price = price;
        this.isFree = (price == 0);
        this.isOnline = isOnline;
        this.status = status != null ? status : EventStatus.LIVE;
        this.imageRes = 0;
    }

    public Event(String id, String title, String description, EventCategory category,
                 String organizer, String organizerId, String venue, String date,
                 String time, int capacity, int price) {
        this(id, title, description, category, organizer, organizerId, venue, "",
                date, time, "", capacity, 0, price, false, EventStatus.LIVE);
    }

    public Event copy() {
        Event copy = new Event(id, title, description, category, organizer, organizerId,
                venue, venueHint, date, time, endTime, capacity, currentRsvps, price,
                isOnline, status);
        copy.imageRes = this.imageRes;
        copy.isFree = this.isFree;
        return copy;
    }
}
