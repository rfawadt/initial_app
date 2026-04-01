package com.tokens.campusevents.ui.notifications;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.Notification;
import com.tokens.campusevents.data.repository.EventRepository;

import java.util.List;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<List<Notification>> notifications = new MutableLiveData<>();

    public MutableLiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public void load() {
        notifications.setValue(EventRepository.getInstance().getNotifications());
    }
}
