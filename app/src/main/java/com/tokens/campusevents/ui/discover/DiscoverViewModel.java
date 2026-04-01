package com.tokens.campusevents.ui.discover;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.repository.UserRepository;
import com.tokens.campusevents.ui.adapter.ScheduleBlockAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiscoverViewModel extends ViewModel {

    private final MutableLiveData<List<ScheduleBlockAdapter.ScheduleHour>> schedule = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasConflict = new MutableLiveData<>(false);
    private final MutableLiveData<String> conflictDescription = new MutableLiveData<>("");

    private static final int[] SCHEDULE_COLORS = {
            R.color.category_academic,
            R.color.category_tech,
            R.color.category_society,
            R.color.category_sports
    };

    public MutableLiveData<List<ScheduleBlockAdapter.ScheduleHour>> getSchedule() {
        return schedule;
    }

    public MutableLiveData<Boolean> getHasConflict() {
        return hasConflict;
    }

    public MutableLiveData<String> getConflictDescription() {
        return conflictDescription;
    }

    public void loadSchedule() {
        String today = getTodayDateString();
        List<Event> allGoingEvents = UserRepository.getInstance().getUserGoingEvents();
        List<Event> goingEvents = new ArrayList<>();
        for (Event e : allGoingEvents) {
            if (today.equals(e.date)) {
                goingEvents.add(e);
            }
        }

        List<ScheduleBlockAdapter.ScheduleHour> hours = new ArrayList<>();
        boolean conflictFound = false;
        StringBuilder conflictDesc = new StringBuilder();

        for (int h = 8; h <= 21; h++) {
            String hourLabel;
            if (h == 12) {
                hourLabel = "12 PM";
            } else if (h > 12) {
                hourLabel = (h - 12) + " PM";
            } else {
                hourLabel = h + " AM";
            }

            Event matchedEvent = null;
            int matchedColor = 0;
            int matchCount = 0;

            for (int i = 0; i < goingEvents.size(); i++) {
                Event event = goingEvents.get(i);
                int startHour = extractHour(event.time);
                int endHour = event.endTime != null && !event.endTime.isEmpty()
                        ? extractHour(event.endTime) : startHour + 1;

                if (startHour <= h && h < endHour) {
                    matchCount++;
                    if (matchedEvent == null) {
                        matchedEvent = event;
                        matchedColor = SCHEDULE_COLORS[i % SCHEDULE_COLORS.length];
                    }
                }
            }

            if (matchCount >= 2) {
                conflictFound = true;
                conflictDesc.append("Conflict at ").append(hourLabel).append(". ");
            }

            hours.add(new ScheduleBlockAdapter.ScheduleHour(hourLabel, matchedEvent, matchedColor));
        }

        schedule.setValue(hours);
        hasConflict.setValue(conflictFound);
        conflictDescription.setValue(conflictDesc.toString().trim());
    }

    private String getTodayDateString() {
        return new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(new Date());
    }

    private int extractHour(String time) {
        if (time == null || time.isEmpty()) return 8;

        try {
            String cleaned = time.trim().toUpperCase();
            boolean isPM = cleaned.contains("PM");
            boolean isAM = cleaned.contains("AM");

            String numberPart = cleaned.replaceAll("[^0-9:]", "").trim();
            String[] parts = numberPart.split(":");
            int hour = Integer.parseInt(parts[0]);

            if (isPM && hour != 12) {
                hour += 12;
            } else if (isAM && hour == 12) {
                hour = 0;
            }

            return hour;
        } catch (NumberFormatException e) {
            return 8;
        }
    }
}
