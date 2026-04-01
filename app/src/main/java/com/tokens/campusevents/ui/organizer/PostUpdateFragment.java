package com.tokens.campusevents.ui.organizer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventUpdate;
import com.tokens.campusevents.data.model.Rsvp;
import com.tokens.campusevents.data.model.UpdateType;
import com.tokens.campusevents.data.repository.EventRepository;
import com.tokens.campusevents.data.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public class PostUpdateFragment extends Fragment {

    private UpdateType selectedType = UpdateType.GENERAL;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

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
        Event event = EventRepository.getInstance().getEventById(eventId);
        if (event == null) {
            navController.navigateUp();
            return;
        }

        // Find views
        View btnCancel = view.findViewById(R.id.btn_cancel);
        TextView tvEventName = view.findViewById(R.id.tv_event_name);
        TextView btnGeneral = view.findViewById(R.id.btn_type_general);
        TextView btnVenue = view.findViewById(R.id.btn_type_venue);
        TextView btnTime = view.findViewById(R.id.btn_type_time);
        TextView btnCancelType = view.findViewById(R.id.btn_type_cancel);
        EditText etMessage = view.findViewById(R.id.et_message);
        TextView tvCharCount = view.findViewById(R.id.tv_char_count);
        CheckBox cbNotify = view.findViewById(R.id.cb_notify);
        TextView tvPeopleCount = view.findViewById(R.id.tv_people_count);
        TextView tvPreviewTitle = view.findViewById(R.id.tv_preview_title);
        TextView tvPreviewType = view.findViewById(R.id.tv_preview_type);
        TextView tvPreviewMessage = view.findViewById(R.id.tv_preview_message);
        TextView btnSendUpdate = view.findViewById(R.id.btn_send_update);

        if (tvEventName != null) {
            tvEventName.setText(event.title);
        }
        if (tvPreviewTitle != null) {
            tvPreviewTitle.setText(event.title);
        }

        // People count from RSVPs
        List<Rsvp> rsvps = UserRepository.getInstance().getRsvpsForEvent(eventId);
        if (tvPeopleCount != null) {
            tvPeopleCount.setText(rsvps.size() + " people will be notified");
        }

        // Type buttons array for easy management
        TextView[] typeButtons = {btnGeneral, btnVenue, btnTime, btnCancelType};
        UpdateType[] types = {UpdateType.GENERAL, UpdateType.VENUE_CHANGE, UpdateType.TIME_CHANGE, UpdateType.CANCELLATION};

        // Set default selection
        updateTypeButtonStyles(typeButtons, 0);
        if (tvPreviewType != null) {
            tvPreviewType.setText(selectedType.getDisplayName());
        }

        // Type button click handlers
        for (int i = 0; i < typeButtons.length; i++) {
            if (typeButtons[i] != null) {
                final int index = i;
                typeButtons[i].setOnClickListener(v -> {
                    selectedType = types[index];
                    updateTypeButtonStyles(typeButtons, index);
                    if (tvPreviewType != null) {
                        tvPreviewType.setText(selectedType.getDisplayName());
                    }
                });
            }
        }

        // Message text watcher
        if (etMessage != null) {
            etMessage.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (tvCharCount != null) {
                        tvCharCount.setText(s.length() + " / 500 characters");
                    }
                    if (tvPreviewMessage != null) {
                        tvPreviewMessage.setText(s.toString());
                    }
                }
            });
        }

        // Cancel button
        if (btnCancel != null) {
            btnCancel.setOnClickListener(v -> navController.navigateUp());
        }

        // Send update button
        final String finalEventId = eventId;
        if (btnSendUpdate != null) {
            btnSendUpdate.setOnClickListener(v -> {
                String message = etMessage != null ? etMessage.getText().toString().trim() : "";
                if (message.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a message",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                EventUpdate update = new EventUpdate(
                        UUID.randomUUID().toString(),
                        finalEventId,
                        message,
                        "Just now",
                        selectedType
                );

                EventRepository.getInstance().postUpdate(update);

                if (selectedType == UpdateType.CANCELLATION) {
                    EventRepository.getInstance().cancelEvent(finalEventId);
                }

                Toast.makeText(requireContext(), "Update sent successfully!",
                        Toast.LENGTH_SHORT).show();
                navController.navigateUp();
            });
        }
    }

    private void updateTypeButtonStyles(TextView[] buttons, int selectedIndex) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null) {
                if (i == selectedIndex) {
                    buttons[i].setBackgroundResource(R.drawable.bg_update_type_selected);
                    buttons[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.text_on_primary));
                } else {
                    buttons[i].setBackgroundResource(R.drawable.bg_update_type);
                    buttons[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary));
                }
            }
        }
    }
}
