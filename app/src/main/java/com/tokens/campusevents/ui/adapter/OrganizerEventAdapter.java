package com.tokens.campusevents.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventStatus;

import java.util.ArrayList;
import java.util.List;

public class OrganizerEventAdapter extends RecyclerView.Adapter<OrganizerEventAdapter.ViewHolder> {

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    private List<Event> events = new ArrayList<>();
    private final OnEventClickListener eventClickListener;

    public OrganizerEventAdapter(OnEventClickListener eventClickListener) {
        this.eventClickListener = eventClickListener;
    }

    public void submitList(List<Event> newEvents) {
        this.events = newEvents != null ? newEvents : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_organizer_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events.get(position);

        // Date, title, venue
        holder.tvDate.setText(event.date);
        holder.tvTitle.setText(event.title);
        holder.tvVenue.setText(event.venue);

        // Thumbnail
        if (event.imageRes != 0) {
            holder.ivThumb.setImageResource(event.imageRes);
        }

        // Status tag
        int bgRes;
        int textColorRes;
        String statusText;

        switch (event.status) {
            case LIVE:
                bgRes = R.drawable.bg_status_live;
                textColorRes = R.color.status_live;
                statusText = "Live";
                break;
            case DRAFT:
                bgRes = R.drawable.bg_status_draft;
                textColorRes = R.color.status_draft;
                statusText = "Draft";
                break;
            case ENDED:
                bgRes = R.drawable.bg_status_ended;
                textColorRes = R.color.status_ended;
                statusText = "Ended";
                break;
            case CANCELLED:
                bgRes = R.drawable.bg_status_ended;
                textColorRes = R.color.status_cancelled;
                statusText = "Cancelled";
                break;
            default:
                bgRes = R.drawable.bg_status_live;
                textColorRes = R.color.status_live;
                statusText = "Live";
                break;
        }

        holder.tvStatus.setText(statusText);
        holder.tvStatus.setBackgroundResource(bgRes);
        holder.tvStatus.setTextColor(ContextCompat.getColor(
                holder.itemView.getContext(), textColorRes));

        // Item click
        holder.itemView.setOnClickListener(v -> {
            if (eventClickListener != null) {
                eventClickListener.onEventClick(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivThumb;
        final TextView tvDate;
        final TextView tvStatus;
        final TextView tvTitle;
        final TextView tvVenue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.iv_thumb);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvVenue = itemView.findViewById(R.id.tv_venue);
        }
    }
}
