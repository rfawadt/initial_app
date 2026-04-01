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
import com.tokens.campusevents.data.model.Event;
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

        // Tab buttons
        TextView tabSaved = view.findViewById(R.id.tab_saved);
        TextView tabRsvps = view.findViewById(R.id.tab_rsvps);

        tabSaved.setOnClickListener(v -> {
            tabSaved.setBackgroundResource(R.drawable.bg_chip_selected);
            tabSaved.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_on_primary));
            tabRsvps.setBackgroundResource(R.drawable.bg_chip);
            tabRsvps.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary));
            viewModel.loadSavedEvents();
        });

        tabRsvps.setOnClickListener(v -> {
            tabRsvps.setBackgroundResource(R.drawable.bg_chip_selected);
            tabRsvps.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_on_primary));
            tabSaved.setBackgroundResource(R.drawable.bg_chip);
            tabSaved.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary));
            viewModel.loadRsvpEvents();
        });

        // Events RecyclerView
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

        // Count text and empty state
        TextView tvCount = view.findViewById(R.id.tv_count);
        View emptyState = view.findViewById(R.id.tv_empty);

        // Observe events
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

        // Default load saved
        viewModel.loadSavedEvents();
    }
}
