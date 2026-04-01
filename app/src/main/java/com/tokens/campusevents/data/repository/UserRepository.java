package com.tokens.campusevents.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.Rsvp;
import com.tokens.campusevents.data.model.RsvpStatus;
import com.tokens.campusevents.data.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserRepository {

    private static UserRepository instance;

    public MutableLiveData<User> currentUser = new MutableLiveData<>(MockData.currentUser);
    public MutableLiveData<Boolean> isOrganizerMode = new MutableLiveData<>(false);
    public MutableLiveData<List<Rsvp>> rsvps = new MutableLiveData<>(MockData.rsvps);

    private UserRepository() {
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User getUser() {
        return MockData.currentUser;
    }

    public void toggleOrganizerMode() {
        Boolean current = isOrganizerMode.getValue();
        isOrganizerMode.setValue(current != null && !current);
    }

    public void setOrganizerMode(boolean isOrganizer) {
        isOrganizerMode.setValue(isOrganizer);
    }

    public RsvpStatus getRsvpForEvent(String eventId) {
        for (Rsvp rsvp : MockData.rsvps) {
            if (rsvp.eventId.equals(eventId) && rsvp.userId.equals(MockData.currentUser.id)) {
                return rsvp.status;
            }
        }
        return RsvpStatus.NONE;
    }

    public boolean setRsvp(String eventId, RsvpStatus status) {
        Event event = EventRepository.getInstance().getEventById(eventId);
        if (event == null) return false;

        // Check capacity for GOING status
        if (status == RsvpStatus.GOING) {
            int goingCount = getGoingCount(eventId);
            // Check if user already has a GOING RSVP (replacing same status)
            boolean alreadyGoing = false;
            for (Rsvp rsvp : MockData.rsvps) {
                if (rsvp.eventId.equals(eventId) && rsvp.userId.equals(MockData.currentUser.id)
                        && rsvp.status == RsvpStatus.GOING) {
                    alreadyGoing = true;
                    break;
                }
            }
            if (!alreadyGoing && goingCount >= event.capacity) {
                return false;
            }
        }

        // Remove existing RSVP for this user and event
        Iterator<Rsvp> iterator = MockData.rsvps.iterator();
        while (iterator.hasNext()) {
            Rsvp rsvp = iterator.next();
            if (rsvp.eventId.equals(eventId) && rsvp.userId.equals(MockData.currentUser.id)) {
                iterator.remove();
                break;
            }
        }

        // Add new RSVP if not NONE
        if (status != RsvpStatus.NONE) {
            User user = MockData.currentUser;
            MockData.rsvps.add(new Rsvp(user.id, user.name, user.email, eventId, status));
        }

        // Update LiveData
        rsvps.setValue(new ArrayList<>(MockData.rsvps));
        return true;
    }

    public void cancelRsvp(String eventId) {
        Iterator<Rsvp> iterator = MockData.rsvps.iterator();
        while (iterator.hasNext()) {
            Rsvp rsvp = iterator.next();
            if (rsvp.eventId.equals(eventId) && rsvp.userId.equals(MockData.currentUser.id)) {
                iterator.remove();
                break;
            }
        }
        rsvps.setValue(new ArrayList<>(MockData.rsvps));
    }

    public List<Rsvp> getRsvpsForEvent(String eventId) {
        List<Rsvp> result = new ArrayList<>();
        for (Rsvp rsvp : MockData.rsvps) {
            if (rsvp.eventId.equals(eventId)) {
                result.add(rsvp);
            }
        }
        return result;
    }

    public int getGoingCount(String eventId) {
        int count = 0;
        for (Rsvp rsvp : MockData.rsvps) {
            if (rsvp.eventId.equals(eventId) && rsvp.status == RsvpStatus.GOING) {
                count++;
            }
        }
        return count;
    }

    public int getInterestedCount(String eventId) {
        int count = 0;
        for (Rsvp rsvp : MockData.rsvps) {
            if (rsvp.eventId.equals(eventId) && rsvp.status == RsvpStatus.INTERESTED) {
                count++;
            }
        }
        return count;
    }

    public List<Event> getUserRsvpEvents() {
        List<Event> result = new ArrayList<>();
        EventRepository eventRepo = EventRepository.getInstance();
        for (Rsvp rsvp : MockData.rsvps) {
            if (rsvp.userId.equals(MockData.currentUser.id)) {
                Event event = eventRepo.getEventById(rsvp.eventId);
                if (event != null) {
                    result.add(event);
                }
            }
        }
        return result;
    }

    public List<Event> getUserGoingEvents() {
        List<Event> result = new ArrayList<>();
        EventRepository eventRepo = EventRepository.getInstance();
        for (Rsvp rsvp : MockData.rsvps) {
            if (rsvp.userId.equals(MockData.currentUser.id) && rsvp.status == RsvpStatus.GOING) {
                Event event = eventRepo.getEventById(rsvp.eventId);
                if (event != null) {
                    result.add(event);
                }
            }
        }
        return result;
    }

    public void toggleSaved(String eventId) {
        User user = MockData.currentUser;
        if (user.savedEventIds.contains(eventId)) {
            user.savedEventIds.remove(eventId);
        } else {
            user.savedEventIds.add(eventId);
        }
        currentUser.setValue(user);
    }

    public boolean isSaved(String eventId) {
        return MockData.currentUser.savedEventIds.contains(eventId);
    }

    public List<Event> getSavedEvents() {
        List<Event> result = new ArrayList<>();
        EventRepository eventRepo = EventRepository.getInstance();
        for (String eventId : MockData.currentUser.savedEventIds) {
            Event event = eventRepo.getEventById(eventId);
            if (event != null) {
                result.add(event);
            }
        }
        return result;
    }

    public void toggleFollow(String organizerId) {
        User user = MockData.currentUser;
        if (user.followedOrganizers.contains(organizerId)) {
            user.followedOrganizers.remove(organizerId);
        } else {
            user.followedOrganizers.add(organizerId);
        }
        currentUser.setValue(user);
    }

    public boolean isFollowing(String organizerId) {
        return MockData.currentUser.followedOrganizers.contains(organizerId);
    }
}
