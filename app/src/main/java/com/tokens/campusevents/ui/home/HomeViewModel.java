package com.tokens.campusevents.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventCategory;
import com.tokens.campusevents.data.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Event>> featuredEvents = new MutableLiveData<>();
    private final MutableLiveData<List<Event>> events = new MutableLiveData<>();
    private final MutableLiveData<EventCategory> selectedCategory = new MutableLiveData<>(EventCategory.ALL);

    private final EventRepository eventRepository = EventRepository.getInstance();

    public MutableLiveData<List<Event>> getFeaturedEvents() {
        return featuredEvents;
    }

    public MutableLiveData<List<Event>> getEvents() {
        return events;
    }

    public MutableLiveData<EventCategory> getSelectedCategory() {
        return selectedCategory;
    }

    public void loadEvents() {
        featuredEvents.setValue(eventRepository.getFeaturedEvents());
        events.setValue(eventRepository.getAllEvents());
    }

    public void filterByCategory(EventCategory category) {
        selectedCategory.setValue(category);
        events.setValue(eventRepository.filterByCategory(category));
    }

    public List<String> getCategories() {
        List<String> categoryNames = new ArrayList<>();
        for (EventCategory cat : EventCategory.values()) {
            categoryNames.add(cat.getDisplayName());
        }
        return categoryNames;
    }
}
