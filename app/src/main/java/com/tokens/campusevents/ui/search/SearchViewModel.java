package com.tokens.campusevents.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventCategory;
import com.tokens.campusevents.data.repository.EventRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<List<Event>> searchResults = new MutableLiveData<>();

    private String currentQuery = "";
    private EventCategory selectedCategory = null;
    private Boolean isOnlineFilter = null;
    private Boolean isFreeFilter = null;

    private final EventRepository eventRepository = EventRepository.getInstance();

    public SearchViewModel() {
        searchResults.setValue(eventRepository.getAllEvents());
    }

    public MutableLiveData<List<Event>> getSearchResults() {
        return searchResults;
    }

    public void search(String query) {
        this.currentQuery = query != null ? query : "";
        applyFilters();
    }

    public void setCategory(EventCategory category) {
        this.selectedCategory = category;
        applyFilters();
    }

    public void setOnlineFilter(Boolean isOnline) {
        this.isOnlineFilter = isOnline;
        applyFilters();
    }

    public void setFreeFilter(Boolean isFree) {
        this.isFreeFilter = isFree;
        applyFilters();
    }

    public void clearAllFilters() {
        this.selectedCategory = null;
        this.isOnlineFilter = null;
        this.isFreeFilter = null;
        applyFilters();
    }

    private void applyFilters() {
        List<Event> results = eventRepository.searchEvents(currentQuery);

        if (selectedCategory != null && selectedCategory != EventCategory.ALL) {
            List<Event> filtered = new ArrayList<>();
            for (Event event : results) {
                if (event.category == selectedCategory) {
                    filtered.add(event);
                }
            }
            results = filtered;
        }

        if (isOnlineFilter != null) {
            List<Event> filtered = new ArrayList<>();
            for (Event event : results) {
                if (event.isOnline == isOnlineFilter) {
                    filtered.add(event);
                }
            }
            results = filtered;
        }

        if (isFreeFilter != null) {
            List<Event> filtered = new ArrayList<>();
            for (Event event : results) {
                if (event.isFree == isFreeFilter) {
                    filtered.add(event);
                }
            }
            results = filtered;
        }

        searchResults.setValue(results);
    }

    public List<String> getFilterChips() {
        return Arrays.asList("All", "Today", "This Week", "Free", "Paid",
                "Society", "Tech", "Arts");
    }
}
