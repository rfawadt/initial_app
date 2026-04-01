package com.tokens.campusevents.ui.organizer;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.ui.adapter.OrganizerEventAdapter;

public class OrganizerDashboardFragment extends Fragment {

    private OrganizerViewModel viewModel;
    private OrganizerEventAdapter eventAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_organizer_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(OrganizerViewModel.class);
        NavController navController = Navigation.findNavController(view);

        TextView tvStatEvents = view.findViewById(R.id.tv_stat_events);
        TextView tvStatRsvps = view.findViewById(R.id.tv_stat_rsvps);
        TextView tvStatViews = view.findViewById(R.id.tv_stat_views);

        RecyclerView rvOrganizerEvents = view.findViewById(R.id.rv_organizer_events);
        rvOrganizerEvents.setLayoutManager(new LinearLayoutManager(requireContext()));

        eventAdapter = new OrganizerEventAdapter(
                event -> {
                    // Item tap → edit event
                    Bundle bundle = new Bundle();
                    bundle.putString("eventId", event.id);
                    navController.navigate(R.id.action_dashboard_to_editEvent, bundle);
                },
                event -> {
                    // View RSVPs button
                    Bundle bundle = new Bundle();
                    bundle.putString("eventId", event.id);
                    navController.navigate(R.id.action_dashboard_to_rsvpList, bundle);
                },
                event -> {
                    // Send Update button
                    Bundle bundle = new Bundle();
                    bundle.putString("eventId", event.id);
                    navController.navigate(R.id.action_dashboard_to_postUpdate, bundle);
                }
        );
        rvOrganizerEvents.setAdapter(eventAdapter);

        View btnCreateEvent = view.findViewById(R.id.btn_create_event);
        if (btnCreateEvent != null) {
            btnCreateEvent.setOnClickListener(v ->
                    navController.navigate(R.id.action_dashboard_to_createEvent));
        }

        viewModel.getTotalEvents().observe(getViewLifecycleOwner(), count -> {
            if (tvStatEvents != null) tvStatEvents.setText(String.valueOf(count));
        });

        viewModel.getTotalRsvps().observe(getViewLifecycleOwner(), count -> {
            if (tvStatRsvps != null) tvStatRsvps.setText(String.valueOf(count));
        });

        viewModel.getTotalViews().observe(getViewLifecycleOwner(), views -> {
            if (tvStatViews != null) tvStatViews.setText(views);
        });

        viewModel.getEvents().observe(getViewLifecycleOwner(), events ->
                eventAdapter.submitList(events));

        viewModel.loadDashboard();
    }
}
