package com.tokens.campusevents.data.repository;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventCategory;
import com.tokens.campusevents.data.model.EventStatus;
import com.tokens.campusevents.data.model.EventUpdate;
import com.tokens.campusevents.data.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private static EventRepository instance;

    private EventRepository() {
    }

    public static synchronized EventRepository getInstance() {
        if (instance == null) {
            instance = new EventRepository();
        }
        return instance;
    }

    public List<Event> getAllEvents() {
        List<Event> result = new ArrayList<>();
        for (Event event : MockData.events) {
            if (event.status != EventStatus.CANCELLED && event.status != EventStatus.ENDED) {
                result.add(event);
            }
        }
        return result;
    }

    public Event getEventById(String id) {
        for (Event event : MockData.events) {
            if (event.id.equals(id)) {
                return event;
            }
        }
        return null;
    }

    public List<Event> getFeaturedEvents() {
        List<Event> result = new ArrayList<>();
        for (Event event : MockData.events) {
            if (event.status == EventStatus.LIVE && result.size() < 3) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Event> getTodayEvents() {
        List<Event> result = new ArrayList<>();
        for (Event event : MockData.events) {
            if (event.status == EventStatus.LIVE && result.size() < 4) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Event> searchEvents(String query) {
        List<Event> result = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return getAllEvents();
        }
        String lowerQuery = query.toLowerCase();
        for (Event event : MockData.events) {
            if (event.status == EventStatus.CANCELLED || event.status == EventStatus.ENDED) continue;
            if (event.title.toLowerCase().contains(lowerQuery)
                    || event.description.toLowerCase().contains(lowerQuery)
                    || event.organizer.toLowerCase().contains(lowerQuery)
                    || event.venue.toLowerCase().contains(lowerQuery)) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Event> filterByCategory(EventCategory category) {
        if (category == EventCategory.ALL) {
            return getAllEvents();
        }
        List<Event> result = new ArrayList<>();
        for (Event event : MockData.events) {
            if (event.status != EventStatus.CANCELLED && event.status != EventStatus.ENDED
                    && event.category == category) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Event> filterByLocation(String location) {
        List<Event> result = new ArrayList<>();
        if (location == null || location.trim().isEmpty()) {
            return getAllEvents();
        }
        String lowerLocation = location.toLowerCase();
        for (Event event : MockData.events) {
            if (event.status != EventStatus.CANCELLED && event.status != EventStatus.ENDED
                    && event.venue.toLowerCase().contains(lowerLocation)) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Event> filterByOnline(boolean isOnline) {
        List<Event> result = new ArrayList<>();
        for (Event event : MockData.events) {
            if (event.status != EventStatus.CANCELLED && event.status != EventStatus.ENDED
                    && event.isOnline == isOnline) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Event> getOrganizerEvents() {
        List<Event> result = new ArrayList<>();
        for (Event event : MockData.events) {
            if ("org_1".equals(event.organizerId) || "org_3".equals(event.organizerId)) {
                result.add(event);
            }
        }
        return result;
    }

    public void createEvent(Event event) {
        MockData.events.add(0, event);
    }

    public void updateEvent(String eventId, Event updated) {
        for (int i = 0; i < MockData.events.size(); i++) {
            if (MockData.events.get(i).id.equals(eventId)) {
                MockData.events.set(i, updated);
                return;
            }
        }
    }

    public void cancelEvent(String eventId) {
        for (Event event : MockData.events) {
            if (event.id.equals(eventId)) {
                event.status = EventStatus.CANCELLED;
                return;
            }
        }
    }

    public List<EventUpdate> getUpdatesForEvent(String eventId) {
        List<EventUpdate> result = new ArrayList<>();
        for (EventUpdate update : MockData.eventUpdates) {
            if (update.eventId.equals(eventId)) {
                result.add(update);
            }
        }
        return result;
    }

    public void postUpdate(EventUpdate update) {
        MockData.eventUpdates.add(0, update);
    }

    public List<Notification> getNotifications() {
        return new ArrayList<>(MockData.notifications);
    }
}
