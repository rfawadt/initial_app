package com.tokens.campusevents.ui.favorites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FavoritesViewModel extends ViewModel {

    private final MutableLiveData<List<Event>> events = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showingSaved = new MutableLiveData<>(true);

    private final UserRepository userRepository = UserRepository.getInstance();

    public MutableLiveData<List<Event>> getEvents() {
        return events;
    }

    public MutableLiveData<Boolean> getShowingSaved() {
        return showingSaved;
    }

    public void loadSavedEvents() {
        showingSaved.setValue(true);
        events.setValue(userRepository.getSavedEvents());
    }

    public void loadRsvpEvents() {
        showingSaved.setValue(false);
        events.setValue(userRepository.getUserRsvpEvents());
    }

    public void loadUpcomingRsvpEvents() {
        showingSaved.setValue(false);
        Date today = new Date();
        List<Event> upcoming = new ArrayList<>();
        for (Event event : userRepository.getUserRsvpEvents()) {
            Date eventDate = parseDate(event.date);
            if (eventDate != null && !eventDate.before(truncateToDay(today))) {
                upcoming.add(event);
            }
        }
        events.setValue(upcoming);
    }

    public void loadPastRsvpEvents() {
        showingSaved.setValue(false);
        Date today = truncateToDay(new Date());
        List<Event> past = new ArrayList<>();
        for (Event event : userRepository.getUserRsvpEvents()) {
            Date eventDate = parseDate(event.date);
            if (eventDate != null && eventDate.before(today)) {
                past.add(event);
            }
        }
        events.setValue(past);
    }

    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    private Date truncateToDay(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
