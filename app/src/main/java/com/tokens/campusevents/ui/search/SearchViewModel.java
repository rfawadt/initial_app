package com.tokens.campusevents.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventCategory;
import com.tokens.campusevents.data.repository.EventRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<List<Event>> searchResults = new MutableLiveData<>();

    private String currentQuery = "";
    private EventCategory selectedCategory = null;
    private Boolean isOnlineFilter = null;
    private Boolean isFreeFilter = null;
    private String locationFilter = null;
    private String dateFilter = null; // "today" | "thisweek" | null

    private static final SimpleDateFormat EVENT_DATE_FMT =
            new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

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

    public void setLocationFilter(String location) {
        this.locationFilter = location;
        applyFilters();
    }

    public void setDateFilter(String filter) {
        this.dateFilter = filter;
        applyFilters();
    }

    public void clearAllFilters() {
        this.selectedCategory = null;
        this.isOnlineFilter = null;
        this.isFreeFilter = null;
        this.locationFilter = null;
        this.dateFilter = null;
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

        if (locationFilter != null && !locationFilter.isEmpty()) {
            String lowerLocation = locationFilter.toLowerCase();
            List<Event> filtered = new ArrayList<>();
            for (Event event : results) {
                if (event.venue.toLowerCase().contains(lowerLocation)) {
                    filtered.add(event);
                }
            }
            results = filtered;
        }

        if ("today".equals(dateFilter)) {
            List<Event> filtered = new ArrayList<>();
            for (Event event : results) {
                if (isToday(event.date)) filtered.add(event);
            }
            results = filtered;
        } else if ("thisweek".equals(dateFilter)) {
            List<Event> filtered = new ArrayList<>();
            for (Event event : results) {
                if (isThisWeek(event.date)) filtered.add(event);
            }
            results = filtered;
        }

        searchResults.setValue(results);
    }

    private boolean isToday(String dateStr) {
        try {
            Date eventDate = EVENT_DATE_FMT.parse(dateStr);
            Calendar eventCal = Calendar.getInstance();
            eventCal.setTime(eventDate);
            Calendar today = Calendar.getInstance();
            return eventCal.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                    && eventCal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR);
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isThisWeek(String dateStr) {
        try {
            Date eventDate = EVENT_DATE_FMT.parse(dateStr);
            Calendar startOfToday = Calendar.getInstance();
            startOfToday.set(Calendar.HOUR_OF_DAY, 0);
            startOfToday.set(Calendar.MINUTE, 0);
            startOfToday.set(Calendar.SECOND, 0);
            startOfToday.set(Calendar.MILLISECOND, 0);
            Calendar endOfWeek = (Calendar) startOfToday.clone();
            endOfWeek.add(Calendar.DAY_OF_YEAR, 6);
            endOfWeek.set(Calendar.HOUR_OF_DAY, 23);
            endOfWeek.set(Calendar.MINUTE, 59);
            endOfWeek.set(Calendar.SECOND, 59);
            return !eventDate.before(startOfToday.getTime())
                    && !eventDate.after(endOfWeek.getTime());
        } catch (ParseException e) {
            return false;
        }
    }

    public List<String> getFilterChips() {
        return Arrays.asList("All", "Today", "This Week", "Free", "Paid",
                "Society", "Tech", "Arts");
    }
}
