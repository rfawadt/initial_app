package com.tokens.campusevents.ui.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.Event;

import java.util.ArrayList;
import java.util.List;

public class ScheduleBlockAdapter extends RecyclerView.Adapter<ScheduleBlockAdapter.ViewHolder> {

    public static class ScheduleHour {
        public String hour;
        public Event event;
        public int color;

        public ScheduleHour(String hour, Event event, int color) {
            this.hour = hour;
            this.event = event;
            this.color = color;
        }
    }

    private List<ScheduleHour> hours = new ArrayList<>();

    public void submitList(List<ScheduleHour> newHours) {
        this.hours = newHours != null ? newHours : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule_block, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleHour schedule = hours.get(position);

        // Hour label
        holder.tvHour.setText(schedule.hour);

        if (schedule.event != null) {
            holder.eventBlock.setVisibility(View.VISIBLE);
            holder.tvEventTitle.setText(schedule.event.title);
            holder.tvEventTime.setText(schedule.event.time);
            holder.tvEventVenue.setText(schedule.event.venue);

            // Set background color with rounded corners
            GradientDrawable blockBg = new GradientDrawable();
            blockBg.setCornerRadius(12f * holder.itemView.getContext().getResources().getDisplayMetrics().density);
            blockBg.setColor(ContextCompat.getColor(
                    holder.itemView.getContext(), schedule.color));
            holder.eventBlock.setBackground(blockBg);
        } else {
            holder.eventBlock.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvHour;
        final LinearLayout eventBlock;
        final TextView tvEventTitle;
        final TextView tvEventTime;
        final TextView tvEventVenue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHour = itemView.findViewById(R.id.tv_hour);
            eventBlock = itemView.findViewById(R.id.event_block);
            tvEventTitle = itemView.findViewById(R.id.tv_event_title);
            tvEventTime = itemView.findViewById(R.id.tv_event_time);
            tvEventVenue = itemView.findViewById(R.id.tv_event_venue);
        }
    }
}
