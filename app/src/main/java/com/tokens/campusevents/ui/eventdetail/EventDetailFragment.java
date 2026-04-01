package com.tokens.campusevents.ui.eventdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.tokens.campusevents.ui.adapter.UpdateAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventDetailFragment extends Fragment {

    private EventDetailViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(EventDetailViewModel.class);
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
        TextView tvCategoryTag = view.findViewById(R.id.tv_category_tag);
        View btnShare = view.findViewById(R.id.btn_share);
        TextView tvTitle = view.findViewById(R.id.tv_event_title);
        TextView tvHostedBy = view.findViewById(R.id.tv_hosted_by);
        TextView btnFollow = view.findViewById(R.id.btn_follow);
        TextView tvDateDay = view.findViewById(R.id.tv_date_day);
        TextView tvDateNum = view.findViewById(R.id.tv_date_num);
        TextView tvVenueCard = view.findViewById(R.id.tv_venue_short);
        TextView tvSeatsCard = view.findViewById(R.id.tv_seats_left);
        ProgressBar seatProgressBar = view.findViewById(R.id.progress_seats);
        TextView tvFullDate = view.findViewById(R.id.tv_full_date);
        TextView tvTime = view.findViewById(R.id.tv_time);
        TextView tvVenueName = view.findViewById(R.id.tv_venue_name);
        TextView tvVenueHint = view.findViewById(R.id.tv_venue_hint);
        TextView tvPrice = view.findViewById(R.id.tv_price);
        TextView tvDescription = view.findViewById(R.id.tv_description);
        TextView btnGetTickets = view.findViewById(R.id.btn_get_tickets);
        RecyclerView rvUpdates = view.findViewById(R.id.rv_updates);
        View updatesSection = view.findViewById(R.id.updates_section);
        View btnBack = view.findViewById(R.id.btn_back);

        // Back button
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> navController.navigateUp());
        }

        // Follow button
        if (btnFollow != null) {
            btnFollow.setOnClickListener(v -> viewModel.toggleFollow());
        }

        // Updates RecyclerView
        UpdateAdapter updateAdapter = new UpdateAdapter();
        if (rvUpdates != null) {
            rvUpdates.setLayoutManager(new LinearLayoutManager(requireContext()));
            rvUpdates.setAdapter(updateAdapter);
        }

        // Observe event
        final String finalEventId = eventId;
        viewModel.getEvent().observe(getViewLifecycleOwner(), event -> {
            if (event == null) return;

            if (tvCategoryTag != null) {
                tvCategoryTag.setText(event.category.getDisplayName().toUpperCase(Locale.getDefault()));
            }
            if (tvTitle != null) {
                tvTitle.setText(event.title);
            }
            if (tvHostedBy != null) {
                tvHostedBy.setText("Hosted by " + event.organizer);
            }
            if (tvDateDay != null && tvDateNum != null) {
                bindDateCard(event.date, tvDateDay, tvDateNum);
            }
            if (tvVenueCard != null) {
                tvVenueCard.setText(getShortVenueLabel(event.venue));
            }
            int seatsRemaining = viewModel.getSeatsRemaining();
            if (tvSeatsCard != null) {
                tvSeatsCard.setText(seatsRemaining + " left");
            }
            if (seatProgressBar != null) {
                seatProgressBar.setProgress(viewModel.getSeatPercentage());
            }
            if (tvFullDate != null) {
                tvFullDate.setText(event.date);
            }
            if (tvTime != null) {
                String timeText = event.time;
                if (event.endTime != null && !event.endTime.isEmpty()) {
                    timeText += " - " + event.endTime;
                }
                tvTime.setText(timeText);
            }
            if (tvVenueName != null) {
                tvVenueName.setText(event.venue);
            }
            if (tvVenueHint != null) {
                if (event.venueHint != null && !event.venueHint.isEmpty()) {
                    tvVenueHint.setText(event.venueHint);
                    tvVenueHint.setVisibility(View.VISIBLE);
                } else {
                    tvVenueHint.setVisibility(View.GONE);
                }
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
            if (tvDescription != null) {
                tvDescription.setText(event.description);
            }
            if (btnShare != null) {
                btnShare.setOnClickListener(v -> {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT,
                            "Check out " + event.title + " on " + event.date + " at " + event.venue);
                    startActivity(Intent.createChooser(shareIntent, "Share Event"));
                });
            }
            if (btnGetTickets != null) {
                btnGetTickets.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("eventId", finalEventId);
                    navController.navigate(R.id.action_eventDetail_to_rsvp, bundle);
                });
            }
        });

        // Observe follow state
        viewModel.getIsFollowing().observe(getViewLifecycleOwner(), isFollowing -> {
            if (btnFollow != null && isFollowing != null) {
                if (isFollowing) {
                    btnFollow.setText(getString(R.string.following));
                    btnFollow.setBackgroundResource(R.drawable.bg_chip_selected);
                    btnFollow.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_on_primary));
                } else {
                    btnFollow.setText(getString(R.string.follow));
                    btnFollow.setBackgroundResource(R.drawable.bg_button_secondary);
                    btnFollow.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary));
                }
            }
        });

        // Observe updates
        viewModel.getUpdates().observe(getViewLifecycleOwner(), updates -> {
            if (updates != null && !updates.isEmpty()) {
                updateAdapter.submitList(updates);
                if (updatesSection != null) {
                    updatesSection.setVisibility(View.VISIBLE);
                }
            } else {
                if (updatesSection != null) {
                    updatesSection.setVisibility(View.GONE);
                }
            }
        });

        // Load event data
        viewModel.loadEvent(eventId);
    }

    private void bindDateCard(String rawDate, TextView tvDateDay, TextView tvDateNum) {
        SimpleDateFormat parser = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        try {
            Date parsedDate = parser.parse(rawDate);
            if (parsedDate != null) {
                tvDateDay.setText(new SimpleDateFormat("EEE, MMM", Locale.getDefault()).format(parsedDate));
                tvDateNum.setText(new SimpleDateFormat("d", Locale.getDefault()).format(parsedDate));
                return;
            }
        } catch (ParseException ignored) {}

        String[] parts = rawDate.split(" ");
        if (parts.length >= 2) {
            String month = parts[0];
            String day = parts[1].replace(",", "");
            tvDateDay.setText(month.substring(0, Math.min(month.length(), 3)));
            tvDateNum.setText(day);
        } else {
            tvDateDay.setText(rawDate);
            tvDateNum.setText("");
        }
    }

    private String getShortVenueLabel(String venue) {
        if (venue == null || venue.trim().isEmpty()) return "";
        String trimmedVenue = venue.trim();
        if (trimmedVenue.equalsIgnoreCase("Online (Zoom)")) return "Online";
        String[] parts = trimmedVenue.split("\\s+");
        return parts[parts.length - 1];
    }
}
