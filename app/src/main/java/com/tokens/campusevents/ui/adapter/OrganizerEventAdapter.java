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
    private final OnEventClickListener editClickListener;
    private final OnEventClickListener rsvpListClickListener;
    private final OnEventClickListener sendUpdateClickListener;

    public OrganizerEventAdapter(OnEventClickListener editClickListener,
                                  OnEventClickListener rsvpListClickListener,
                                  OnEventClickListener sendUpdateClickListener) {
        this.editClickListener = editClickListener;
        this.rsvpListClickListener = rsvpListClickListener;
        this.sendUpdateClickListener = sendUpdateClickListener;
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

        holder.tvDate.setText(event.date);
        holder.tvTitle.setText(event.title);
        holder.tvVenue.setText(event.venue);

        if (event.imageRes != 0) {
            holder.ivThumb.setImageResource(event.imageRes);
        }

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

        holder.itemView.setOnClickListener(v -> {
            if (editClickListener != null) editClickListener.onEventClick(event);
        });

        if (holder.btnViewRsvps != null) {
            holder.btnViewRsvps.setOnClickListener(v -> {
                if (rsvpListClickListener != null) rsvpListClickListener.onEventClick(event);
            });
        }

        if (holder.btnSendUpdate != null) {
            holder.btnSendUpdate.setOnClickListener(v -> {
                if (sendUpdateClickListener != null) sendUpdateClickListener.onEventClick(event);
            });
        }
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
        final TextView btnViewRsvps;
        final TextView btnSendUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.iv_thumb);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvVenue = itemView.findViewById(R.id.tv_venue);
            btnViewRsvps = itemView.findViewById(R.id.btn_view_rsvps);
            btnSendUpdate = itemView.findViewById(R.id.btn_send_update);
        }
    }
}
