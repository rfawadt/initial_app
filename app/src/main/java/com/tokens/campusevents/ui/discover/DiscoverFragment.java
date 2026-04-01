package com.tokens.campusevents.ui.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.ui.adapter.ScheduleBlockAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiscoverFragment extends Fragment {

    private DiscoverViewModel viewModel;
    private ScheduleBlockAdapter scheduleAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);

        // Set current date
        TextView tvDate = view.findViewById(R.id.tv_date);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault());
        tvDate.setText(sdf.format(new Date()));

        // Schedule RecyclerView
        RecyclerView rvSchedule = view.findViewById(R.id.rv_schedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(requireContext()));
        scheduleAdapter = new ScheduleBlockAdapter();
        rvSchedule.setAdapter(scheduleAdapter);

        // Conflict banner
        View conflictBanner = view.findViewById(R.id.conflict_banner);
        TextView tvConflictDescription = view.findViewById(R.id.tv_conflict_desc);

        // Empty state
        View emptyState = view.findViewById(R.id.tv_empty);

        // Observe schedule
        viewModel.getSchedule().observe(getViewLifecycleOwner(), hours -> {
            scheduleAdapter.submitList(hours);
            boolean hasEvents = false;
            for (ScheduleBlockAdapter.ScheduleHour hour : hours) {
                if (hour.event != null) {
                    hasEvents = true;
                    break;
                }
            }
            if (emptyState != null) {
                emptyState.setVisibility(hasEvents ? View.GONE : View.VISIBLE);
            }
            rvSchedule.setVisibility(hasEvents ? View.VISIBLE : View.GONE);
        });

        // Observe conflict
        viewModel.getHasConflict().observe(getViewLifecycleOwner(), hasConflict -> {
            if (conflictBanner != null) {
                conflictBanner.setVisibility(hasConflict ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.getConflictDescription().observe(getViewLifecycleOwner(), description -> {
            if (tvConflictDescription != null) {
                tvConflictDescription.setText(description);
            }
        });

        // Load schedule
        viewModel.loadSchedule();
    }
}
