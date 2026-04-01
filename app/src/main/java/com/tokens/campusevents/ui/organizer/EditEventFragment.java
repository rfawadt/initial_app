package com.tokens.campusevents.ui.organizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.repository.EventRepository;

public class EditEventFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        EventRepository eventRepository = EventRepository.getInstance();

        // Get eventId
        String eventId = null;
        if (getArguments() != null) {
            eventId = getArguments().getString("eventId");
        }

        if (eventId == null) {
            navController.navigateUp();
            return;
        }

        // Load event
        Event event = eventRepository.getEventById(eventId);
        if (event == null) {
            navController.navigateUp();
            return;
        }

        // Find views
        View btnBack = view.findViewById(R.id.btn_back);
        EditText etEventName = view.findViewById(R.id.et_event_name);
        EditText etDescription = view.findViewById(R.id.et_description);
        EditText etVenue = view.findViewById(R.id.et_venue);
        EditText etCapacity = view.findViewById(R.id.et_capacity);
        View btnSaveChanges = view.findViewById(R.id.btn_save_changes);
        View btnCancelEvent = view.findViewById(R.id.btn_cancel_event);

        // Pre-fill fields
        if (etEventName != null) {
            etEventName.setText(event.title);
        }
        if (etDescription != null) {
            etDescription.setText(event.description);
        }
        if (etVenue != null) {
            etVenue.setText(event.venue);
        }
        if (etCapacity != null) {
            etCapacity.setText(String.valueOf(event.capacity));
        }

        // Back button
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> navController.navigateUp());
        }

        // Save changes
        final String finalEventId = eventId;
        if (btnSaveChanges != null) {
            btnSaveChanges.setOnClickListener(v -> {
                Event updated = event.copy();
                if (etEventName != null) {
                    updated.title = etEventName.getText().toString().trim();
                }
                if (etDescription != null) {
                    updated.description = etDescription.getText().toString().trim();
                }
                if (etVenue != null) {
                    updated.venue = etVenue.getText().toString().trim();
                }
                if (etCapacity != null) {
                    try {
                        updated.capacity = Integer.parseInt(etCapacity.getText().toString().trim());
                    } catch (NumberFormatException e) {
                        // Keep original capacity
                    }
                }

                eventRepository.updateEvent(finalEventId, updated);
                Toast.makeText(requireContext(), "Event updated successfully!",
                        Toast.LENGTH_SHORT).show();
                navController.navigateUp();
            });
        }

        // Cancel event
        if (btnCancelEvent != null) {
            btnCancelEvent.setOnClickListener(v -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Cancel Event")
                        .setMessage("Are you sure you want to cancel this event? This action cannot be undone.")
                        .setPositiveButton("Yes, Cancel", (dialog, which) -> {
                            eventRepository.cancelEvent(finalEventId);
                            Toast.makeText(requireContext(), "Event cancelled",
                                    Toast.LENGTH_SHORT).show();
                            navController.navigateUp();
                        })
                        .setNegativeButton("No", null)
                        .show();
            });
        }
    }
}
