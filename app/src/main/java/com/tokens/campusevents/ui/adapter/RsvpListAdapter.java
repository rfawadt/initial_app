package com.tokens.campusevents.ui.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.Rsvp;
import com.tokens.campusevents.data.model.RsvpStatus;

import java.util.ArrayList;
import java.util.List;

public class RsvpListAdapter extends RecyclerView.Adapter<RsvpListAdapter.ViewHolder> {

    private List<Rsvp> rsvps = new ArrayList<>();

    public void submitList(List<Rsvp> newRsvps) {
        this.rsvps = newRsvps != null ? newRsvps : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rsvp_attendee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rsvp rsvp = rsvps.get(position);

        // Name and email
        holder.tvName.setText(rsvp.userName);
        holder.tvEmail.setText(rsvp.userEmail);

        // Status badge
        if (rsvp.status == RsvpStatus.GOING) {
            holder.tvStatus.setText("Going");
            holder.tvStatus.setBackgroundResource(R.drawable.bg_chip_selected);
            holder.tvStatus.setTextColor(ContextCompat.getColor(
                    holder.itemView.getContext(), R.color.text_on_primary));
        } else if (rsvp.status == RsvpStatus.INTERESTED) {
            holder.tvStatus.setText("Interested");
            GradientDrawable interestedBg = new GradientDrawable();
            interestedBg.setCornerRadius(12f);
            interestedBg.setColor(ContextCompat.getColor(
                    holder.itemView.getContext(), R.color.rsvp_interested));
            holder.tvStatus.setBackground(interestedBg);
            holder.tvStatus.setTextColor(ContextCompat.getColor(
                    holder.itemView.getContext(), R.color.text_on_primary));
        }
    }

    @Override
    public int getItemCount() {
        return rsvps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivAvatar;
        final TextView tvName;
        final TextView tvEmail;
        final TextView tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
