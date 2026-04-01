package com.tokens.campusevents.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.repository.UserRepository;
import com.tokens.campusevents.ui.adapter.EventCardAdapter;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel viewModel;
    private EventCardAdapter eventCardAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        NavController navController = Navigation.findNavController(view);

        TextView tabSaved = view.findViewById(R.id.tab_saved);
        TextView tabRsvps = view.findViewById(R.id.tab_rsvps);
        View layoutRsvpSubtabs = view.findViewById(R.id.layout_rsvp_subtabs);
        TextView tabUpcoming = view.findViewById(R.id.tab_upcoming);
        TextView tabPast = view.findViewById(R.id.tab_past);

        tabSaved.setOnClickListener(v -> {
            setTabSelected(tabSaved, tabRsvps);
            if (layoutRsvpSubtabs != null) layoutRsvpSubtabs.setVisibility(View.GONE);
            viewModel.loadSavedEvents();
        });

        tabRsvps.setOnClickListener(v -> {
            setTabSelected(tabRsvps, tabSaved);
            if (layoutRsvpSubtabs != null) layoutRsvpSubtabs.setVisibility(View.VISIBLE);
            // Default to Upcoming when switching to RSVPs tab
            if (tabUpcoming != null) setTabSelected(tabUpcoming, tabPast);
            viewModel.loadUpcomingRsvpEvents();
        });

        if (tabUpcoming != null) {
            tabUpcoming.setOnClickListener(v -> {
                setTabSelected(tabUpcoming, tabPast);
                viewModel.loadUpcomingRsvpEvents();
            });
        }

        if (tabPast != null) {
            tabPast.setOnClickListener(v -> {
                setTabSelected(tabPast, tabUpcoming);
                viewModel.loadPastRsvpEvents();
            });
        }

        RecyclerView rvEvents = view.findViewById(R.id.rv_events);
        rvEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
        eventCardAdapter = new EventCardAdapter(
                event -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("eventId", event.id);
                    navController.navigate(R.id.action_favorites_to_eventDetail, bundle);
                },
                event -> UserRepository.getInstance().toggleSaved(event.id),
                event -> {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT,
                            "Check out " + event.title + " on " + event.date + " at " + event.venue);
                    startActivity(Intent.createChooser(shareIntent, "Share Event"));
                });
        rvEvents.setAdapter(eventCardAdapter);

        TextView tvCount = view.findViewById(R.id.tv_count);
        View emptyState = view.findViewById(R.id.tv_empty);

        viewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            eventCardAdapter.submitList(events);
            if (tvCount != null) {
                tvCount.setText(events.size() + " events");
            }
            if (emptyState != null) {
                emptyState.setVisibility(events.isEmpty() ? View.VISIBLE : View.GONE);
            }
            rvEvents.setVisibility(events.isEmpty() ? View.GONE : View.VISIBLE);
        });

        viewModel.loadSavedEvents();
    }

    private void setTabSelected(TextView selected, TextView unselected) {
        if (selected == null || unselected == null) return;
        selected.setBackgroundResource(R.drawable.bg_chip_selected);
        selected.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_on_primary));
        unselected.setBackgroundResource(R.drawable.bg_chip);
        unselected.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary));
    }
}
