package com.tokens.campusevents.ui.rsvp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.RsvpStatus;
import com.tokens.campusevents.data.repository.EventRepository;
import com.tokens.campusevents.data.repository.UserRepository;

public class RsvpViewModel extends ViewModel {

    private final MutableLiveData<Event> event = new MutableLiveData<>();
    private final MutableLiveData<RsvpStatus> rsvpStatus = new MutableLiveData<>(RsvpStatus.NONE);
    private final MutableLiveData<Boolean> confirmResult = new MutableLiveData<>();

    private final EventRepository eventRepository = EventRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    public MutableLiveData<Event> getEvent() {
        return event;
    }

    public MutableLiveData<RsvpStatus> getRsvpStatus() {
        return rsvpStatus;
    }

    public MutableLiveData<Boolean> getConfirmResult() {
        return confirmResult;
    }

    public void loadEvent(String eventId) {
        Event e = eventRepository.getEventById(eventId);
        if (e != null) {
            event.setValue(e);
            rsvpStatus.setValue(userRepository.getRsvpForEvent(eventId));
        }
    }

    public void selectStatus(RsvpStatus status) {
        rsvpStatus.setValue(status);
    }

    public void confirmRsvp() {
        Event e = event.getValue();
        RsvpStatus status = rsvpStatus.getValue();
        if (e != null && status != null && status != RsvpStatus.NONE) {
            boolean success = userRepository.setRsvp(e.id, status);
            confirmResult.setValue(success);
        }
    }

    public void cancelRsvp() {
        Event e = event.getValue();
        if (e != null) {
            userRepository.cancelRsvp(e.id);
            rsvpStatus.setValue(RsvpStatus.NONE);
        }
    }

    public int getSeatsRemaining() {
        Event e = event.getValue();
        if (e == null) return 0;
        int goingCount = userRepository.getGoingCount(e.id);
        return Math.max(0, e.capacity - goingCount);
    }
}
