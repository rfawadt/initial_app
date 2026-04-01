package com.tokens.campusevents.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tokens.campusevents.data.model.User;
import com.tokens.campusevents.data.repository.UserRepository;

public class ProfileViewModel extends ViewModel {

    public LiveData<User> currentUser = UserRepository.getInstance().currentUser;
    public LiveData<Boolean> isOrganizerMode = UserRepository.getInstance().isOrganizerMode;
}
