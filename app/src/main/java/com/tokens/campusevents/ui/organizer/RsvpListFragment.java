package com.tokens.campusevents.ui.organizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.Rsvp;
import com.tokens.campusevents.data.model.RsvpStatus;
import com.tokens.campusevents.data.repository.EventRepository;
import com.tokens.campusevents.data.repository.UserRepository;
import com.tokens.campusevents.ui.adapter.RsvpListAdapter;

import java.util.List;

public class RsvpListFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rsvp_list, container, false);
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
        TextView tvEventName = view.findViewById(R.id.tv_event_name);
        if (tvEventName != null && event != null) {
            tvEventName.setText(event.title);
        }

        // Get RSVPs
        List<Rsvp> rsvps = UserRepository.getInstance().getRsvpsForEvent(eventId);

        // Count going and interested
        int goingCount = 0;
        int interestedCount = 0;
        for (Rsvp rsvp : rsvps) {
            if (rsvp.status == RsvpStatus.GOING) {
                goingCount++;
            } else if (rsvp.status == RsvpStatus.INTERESTED) {
                interestedCount++;
            }
        }

        // Display counts
        TextView tvGoingCount = view.findViewById(R.id.tv_going_count);
        TextView tvInterestedCount = view.findViewById(R.id.tv_interested_count);
        if (tvGoingCount != null) {
            tvGoingCount.setText(goingCount + " Going");
        }
        if (tvInterestedCount != null) {
            tvInterestedCount.setText(interestedCount + " Interested");
        }

        // RSVP list RecyclerView
        RecyclerView rvRsvpList = view.findViewById(R.id.rv_rsvp_list);
        rvRsvpList.setLayoutManager(new LinearLayoutManager(requireContext()));
        RsvpListAdapter adapter = new RsvpListAdapter();
        adapter.submitList(rsvps);
        rvRsvpList.setAdapter(adapter);

        // Empty state
        View emptyState = view.findViewById(R.id.tv_empty);
        if (emptyState != null) {
            emptyState.setVisibility(rsvps.isEmpty() ? View.VISIBLE : View.GONE);
        }
        rvRsvpList.setVisibility(rsvps.isEmpty() ? View.GONE : View.VISIBLE);

        // Back button
        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> navController.navigateUp());
        }
    }
}
