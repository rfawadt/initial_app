package com.tokens.campusevents.ui.organizer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventCategory;
import com.tokens.campusevents.data.model.EventStatus;
import com.tokens.campusevents.data.repository.EventRepository;
import com.tokens.campusevents.data.repository.UserRepository;

import java.util.UUID;

public class CreateEventViewModel extends ViewModel {

    private final MutableLiveData<Boolean> eventCreated = new MutableLiveData<>();

    public MutableLiveData<Boolean> getEventCreated() {
        return eventCreated;
    }

    public void createEvent(String title, String description, String category, String venue,
                            String date, String time, int capacity, int price, boolean isDraft) {
        EventCategory eventCategory = EventCategory.ALL;
        for (EventCategory cat : EventCategory.values()) {
            if (cat.getDisplayName().equalsIgnoreCase(category)) {
                eventCategory = cat;
                break;
            }
        }

        String userId = UserRepository.getInstance().getUser().id;
        String userName = UserRepository.getInstance().getUser().name;

        Event event = new Event(
                UUID.randomUUID().toString(),
                title,
                description,
                eventCategory,
                userName,
                userId,
                venue,
                date,
                time,
                capacity,
                price
        );

        if (isDraft) {
            event.status = EventStatus.DRAFT;
        }

        EventRepository.getInstance().createEvent(event);
        eventCreated.setValue(true);
    }
}
