package com.tokens.campusevents.ui.home;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventCategory;
import com.tokens.campusevents.data.model.User;
import com.tokens.campusevents.data.repository.UserRepository;
import com.tokens.campusevents.ui.adapter.CategoryChipAdapter;
import com.tokens.campusevents.ui.adapter.EventCardAdapter;
import com.tokens.campusevents.ui.adapter.FeaturedEventAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FeaturedEventAdapter featuredAdapter;
    private EventCardAdapter eventCardAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        NavController navController = Navigation.findNavController(view);

        // Greeting text
        TextView tvGreeting = view.findViewById(R.id.tv_greeting);
        User user = UserRepository.getInstance().getUser();
        if (user != null && user.name != null) {
            String firstName = user.name.split(" ")[0];
            tvGreeting.setText("Hey, " + firstName + "!");
        }

        // Notifications button
        View btnNotifications = view.findViewById(R.id.btn_notifications);
        btnNotifications.setOnClickListener(v ->
                navController.navigate(R.id.action_home_to_notifications));

        // Search bar click
        View searchBar = view.findViewById(R.id.search_bar);
        searchBar.setOnClickListener(v ->
                navController.navigate(R.id.nav_search));

        // Categories RecyclerView
        RecyclerView rvCategories = view.findViewById(R.id.rv_categories);
        rvCategories.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false));
        CategoryChipAdapter categoryAdapter = new CategoryChipAdapter(
                viewModel.getCategories(),
                category -> {
                    for (EventCategory cat : EventCategory.values()) {
                        if (cat.getDisplayName().equals(category)) {
                            viewModel.filterByCategory(cat);
                            break;
                        }
                    }
                });
        rvCategories.setAdapter(categoryAdapter);

        // Featured Events RecyclerView
        RecyclerView rvFeatured = view.findViewById(R.id.rv_featured);
        rvFeatured.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false));
        featuredAdapter = new FeaturedEventAdapter(
                event -> navigateToDetail(navController, event),
                event -> UserRepository.getInstance().toggleSaved(event.id));
        rvFeatured.setAdapter(featuredAdapter);

        // Events RecyclerView
        RecyclerView rvEvents = view.findViewById(R.id.rv_events);
        rvEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
        eventCardAdapter = new EventCardAdapter(
                event -> navigateToDetail(navController, event),
                event -> UserRepository.getInstance().toggleSaved(event.id),
                event -> shareEvent(event));
        rvEvents.setAdapter(eventCardAdapter);

        // Observe data
        viewModel.getFeaturedEvents().observe(getViewLifecycleOwner(), events ->
                featuredAdapter.submitList(events));

        viewModel.getEvents().observe(getViewLifecycleOwner(), events ->
                eventCardAdapter.submitList(events));

        // Load data
        viewModel.loadEvents();
    }

    private void navigateToDetail(NavController navController, Event event) {
        Bundle bundle = new Bundle();
        bundle.putString("eventId", event.id);
        navController.navigate(R.id.action_home_to_eventDetail, bundle);
    }

    private void shareEvent(Event event) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "Check out " + event.title + " on " + event.date + " at " + event.venue);
        startActivity(Intent.createChooser(shareIntent, "Share Event"));
    }
}
