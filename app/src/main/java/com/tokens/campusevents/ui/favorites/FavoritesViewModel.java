package com.tokens.campusevents.ui.favorites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.repository.UserRepository;

import java.util.List;

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
}
