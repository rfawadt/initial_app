package com.tokens.campusevents.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tokens.campusevents.LoginActivity;
import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.User;
import com.tokens.campusevents.data.repository.UserRepository;

public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        NavController navController = Navigation.findNavController(view);

        // User info
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvEmail = view.findViewById(R.id.tv_email);
        TextView tvCity = view.findViewById(R.id.tv_city);

        User user = UserRepository.getInstance().getUser();
        if (user != null) {
            tvName.setText(user.name);
            tvEmail.setText(user.email);
            if (tvCity != null) {
                tvCity.setText(user.city);
            }
        }

        // Switch mode button
        TextView btnSwitchMode = view.findViewById(R.id.btn_switch_mode);
        viewModel.isOrganizerMode.observe(getViewLifecycleOwner(), isOrganizer -> {
            if (btnSwitchMode != null) {
                btnSwitchMode.setText(isOrganizer != null && isOrganizer
                        ? "Switch to Student Mode"
                        : "Switch to Organizer Mode");
            }
        });

        if (btnSwitchMode != null) {
            btnSwitchMode.setOnClickListener(v -> {
                UserRepository.getInstance().toggleOrganizerMode();
                Boolean isOrganizer = UserRepository.getInstance().isOrganizerMode.getValue();
                if (isOrganizer != null && isOrganizer) {
                    navController.navigate(R.id.action_profile_to_organizerDashboard);
                }
            });
        }

        // Manage events button
        View btnManageEvents = view.findViewById(R.id.btn_manage_events);
        if (btnManageEvents != null) {
            btnManageEvents.setOnClickListener(v ->
                    navController.navigate(R.id.action_profile_to_organizerDashboard));
        }

        // Logout button
        View btnLogout = view.findViewById(R.id.btn_logout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                if (getActivity() != null) {
                    getActivity().finish();
                }
            });
        }
    }
}
