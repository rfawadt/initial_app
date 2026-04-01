package com.tokens.campusevents.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.Notification;
import com.tokens.campusevents.data.repository.EventRepository;
import com.tokens.campusevents.ui.adapter.NotificationAdapter;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        NavController navController = Navigation.findNavController(view);

        // Back button
        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> navController.navigateUp());
        }

        // Notifications RecyclerView
        RecyclerView rvNotifications = view.findViewById(R.id.rv_notifications);
        rvNotifications.setLayoutManager(new LinearLayoutManager(requireContext()));
        NotificationAdapter adapter = new NotificationAdapter();
        rvNotifications.setAdapter(adapter);

        // Empty state
        View emptyState = view.findViewById(R.id.tv_empty);

        // Observe notifications
        viewModel.getNotifications().observe(getViewLifecycleOwner(), notifications -> {
            adapter.submitList(notifications);
            if (emptyState != null) {
                emptyState.setVisibility(notifications.isEmpty() ? View.VISIBLE : View.GONE);
            }
            rvNotifications.setVisibility(notifications.isEmpty() ? View.GONE : View.VISIBLE);
        });

        // Load notifications
        viewModel.load();
    }
}
