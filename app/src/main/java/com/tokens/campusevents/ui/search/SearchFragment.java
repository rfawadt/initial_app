package com.tokens.campusevents.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.tokens.campusevents.data.repository.UserRepository;
import com.tokens.campusevents.ui.adapter.CategoryChipAdapter;
import com.tokens.campusevents.ui.adapter.EventCardAdapter;

import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private EventCardAdapter eventCardAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        NavController navController = Navigation.findNavController(view);

        // Search input
        EditText etSearch = view.findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.search(s.toString());
            }
        });

        // Filter chips
        RecyclerView rvFilterChips = view.findViewById(R.id.rv_filter_chips);
        rvFilterChips.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false));
        CategoryChipAdapter chipAdapter = new CategoryChipAdapter(
                viewModel.getFilterChips(),
                chip -> {
                    // Handle chip selection for filtering
                });
        rvFilterChips.setAdapter(chipAdapter);

        // Search results
        RecyclerView rvSearchResults = view.findViewById(R.id.rv_search_results);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        eventCardAdapter = new EventCardAdapter(
                event -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("eventId", event.id);
                    navController.navigate(R.id.action_search_to_eventDetail, bundle);
                },
                event -> UserRepository.getInstance().toggleSaved(event.id),
                event -> {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT,
                            "Check out " + event.title + " on " + event.date + " at " + event.venue);
                    startActivity(Intent.createChooser(shareIntent, "Share Event"));
                });
        rvSearchResults.setAdapter(eventCardAdapter);

        // Observe search results
        TextView tvResultCount = view.findViewById(R.id.tv_results_count);
        View emptyState = view.findViewById(R.id.tv_empty);

        viewModel.getSearchResults().observe(getViewLifecycleOwner(), events -> {
            eventCardAdapter.submitList(events);
            if (tvResultCount != null) {
                tvResultCount.setText(events.size() + " events found");
            }
            if (emptyState != null) {
                emptyState.setVisibility(events.isEmpty() ? View.VISIBLE : View.GONE);
            }
            rvSearchResults.setVisibility(events.isEmpty() ? View.GONE : View.VISIBLE);
        });
    }
}
