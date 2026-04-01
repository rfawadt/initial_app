package com.tokens.campusevents.ui.eventdetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventUpdate;
import com.tokens.campusevents.data.model.RsvpStatus;
import com.tokens.campusevents.data.repository.EventRepository;
import com.tokens.campusevents.data.repository.UserRepository;

import java.util.List;

public class EventDetailViewModel extends ViewModel {

    private final MutableLiveData<Event> event = new MutableLiveData<>();
    private final MutableLiveData<RsvpStatus> rsvpStatus = new MutableLiveData<>();
    private final MutableLiveData<List<EventUpdate>> updates = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFollowing = new MutableLiveData<>();

    private final EventRepository eventRepository = EventRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    public MutableLiveData<Event> getEvent() {
        return event;
    }

    public MutableLiveData<RsvpStatus> getRsvpStatus() {
        return rsvpStatus;
    }

    public MutableLiveData<List<EventUpdate>> getUpdates() {
        return updates;
    }

    public MutableLiveData<Boolean> getIsFollowing() {
        return isFollowing;
    }

    public void loadEvent(String eventId) {
        Event e = eventRepository.getEventById(eventId);
        if (e != null) {
            event.setValue(e);
            rsvpStatus.setValue(userRepository.getRsvpForEvent(eventId));
            updates.setValue(eventRepository.getUpdatesForEvent(eventId));
            isFollowing.setValue(userRepository.isFollowing(e.organizerId));
        }
    }

    public void toggleFollow() {
        Event e = event.getValue();
        if (e != null) {
            userRepository.toggleFollow(e.organizerId);
            isFollowing.setValue(userRepository.isFollowing(e.organizerId));
        }
    }

    public int getSeatsRemaining() {
        Event e = event.getValue();
        if (e == null) return 0;
        int goingCount = userRepository.getGoingCount(e.id);
        return Math.max(0, e.capacity - goingCount);
    }

    public int getSeatPercentage() {
        Event e = event.getValue();
        if (e == null || e.capacity == 0) return 0;
        int goingCount = userRepository.getGoingCount(e.id);
        int percentage = (goingCount * 100) / e.capacity;
        return Math.max(0, Math.min(100, percentage));
    }
}
