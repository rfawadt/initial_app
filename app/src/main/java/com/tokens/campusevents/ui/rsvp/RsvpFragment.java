package com.tokens.campusevents.ui.rsvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.RsvpStatus;

public class RsvpFragment extends Fragment {

    private RsvpViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rsvp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RsvpViewModel.class);
        NavController navController = Navigation.findNavController(view);

        // Get eventId from arguments
        String eventId = null;
        if (getArguments() != null) {
            eventId = getArguments().getString("eventId");
        }

        if (eventId == null) {
            navController.navigateUp();
            return;
        }

        // Find views
        View btnBack = view.findViewById(R.id.btn_back);
        TextView tvEventInfo = view.findViewById(R.id.tv_event_info);
        TextView tvPrice = view.findViewById(R.id.tv_price);
        TextView tvSeatsInfo = view.findViewById(R.id.tv_seats_info);
        TextView btnGoing = view.findViewById(R.id.btn_going);
        TextView btnInterested = view.findViewById(R.id.btn_interested);
        TextView btnConfirmRsvp = view.findViewById(R.id.btn_confirm_rsvp);

        // Back button
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> navController.navigateUp());
        }

        // Observe event
        viewModel.getEvent().observe(getViewLifecycleOwner(), event -> {
            if (event == null) return;

            if (tvEventInfo != null) {
                tvEventInfo.setText(event.title + " - " + event.date);
            }
            if (tvPrice != null) {
                if (event.isFree) {
                    tvPrice.setText("Free");
                    tvPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.free_tag));
                } else {
                    tvPrice.setText("PKR " + event.price);
                    tvPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.price_tag));
                }
            }
            if (tvSeatsInfo != null) {
                tvSeatsInfo.setText(viewModel.getSeatsRemaining() + " seats remaining");
            }
        });

        // Observe RSVP status for button styles
        viewModel.getRsvpStatus().observe(getViewLifecycleOwner(), status -> {
            if (btnGoing != null) {
                if (status == RsvpStatus.GOING) {
                    btnGoing.setBackgroundResource(R.drawable.bg_chip_selected);
                    btnGoing.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_on_primary));
                } else {
                    btnGoing.setBackgroundResource(R.drawable.bg_button_secondary);
                    btnGoing.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary));
                }
            }
            if (btnInterested != null) {
                if (status == RsvpStatus.INTERESTED) {
                    btnInterested.setBackgroundResource(R.drawable.bg_chip_selected);
                    btnInterested.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_on_primary));
                } else {
                    btnInterested.setBackgroundResource(R.drawable.bg_button_secondary);
                    btnInterested.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary));
                }
            }
        });

        // Button clicks
        if (btnGoing != null) {
            btnGoing.setOnClickListener(v -> viewModel.selectStatus(RsvpStatus.GOING));
        }
        if (btnInterested != null) {
            btnInterested.setOnClickListener(v -> viewModel.selectStatus(RsvpStatus.INTERESTED));
        }

        // Confirm RSVP
        if (btnConfirmRsvp != null) {
            btnConfirmRsvp.setOnClickListener(v -> {
                RsvpStatus status = viewModel.getRsvpStatus().getValue();
                if (status == null || status == RsvpStatus.NONE) {
                    Toast.makeText(requireContext(), "Please select Going or Interested",
                            Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.confirmRsvp();
                }
            });
        }

        // Observe confirm result
        viewModel.getConfirmResult().observe(getViewLifecycleOwner(), success -> {
            if (success != null) {
                if (success) {
                    Toast.makeText(requireContext(), "RSVP Confirmed!",
                            Toast.LENGTH_SHORT).show();
                    navController.navigateUp();
                } else {
                    Toast.makeText(requireContext(), "Event is Full",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load event data
        viewModel.loadEvent(eventId);
    }
}
