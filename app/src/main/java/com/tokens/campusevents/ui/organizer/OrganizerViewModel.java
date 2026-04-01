package com.tokens.campusevents.ui.organizer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.RsvpStatus;
import com.tokens.campusevents.data.model.Rsvp;
import com.tokens.campusevents.data.repository.EventRepository;
import com.tokens.campusevents.data.repository.UserRepository;

import java.util.List;

public class OrganizerViewModel extends ViewModel {

    private final MutableLiveData<List<Event>> events = new MutableLiveData<>();
    private final MutableLiveData<Integer> totalEvents = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> totalRsvps = new MutableLiveData<>(0);
    private final MutableLiveData<String> totalViews = new MutableLiveData<>("0");

    private final EventRepository eventRepository = EventRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    public MutableLiveData<List<Event>> getEvents() {
        return events;
    }

    public MutableLiveData<Integer> getTotalEvents() {
        return totalEvents;
    }

    public MutableLiveData<Integer> getTotalRsvps() {
        return totalRsvps;
    }

    public MutableLiveData<String> getTotalViews() {
        return totalViews;
    }

    public void loadDashboard() {
        List<Event> organizerEvents = eventRepository.getOrganizerEvents();
        events.setValue(organizerEvents);
        totalEvents.setValue(organizerEvents.size());

        int rsvpCount = 0;
        for (Event event : organizerEvents) {
            List<Rsvp> eventRsvps = userRepository.getRsvpsForEvent(event.id);
            rsvpCount += eventRsvps.size();
        }
        totalRsvps.setValue(rsvpCount);
        totalViews.setValue("1.2K");
    }
}
