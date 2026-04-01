package com.tokens.campusevents.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.EventUpdate;

import java.util.ArrayList;
import java.util.List;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.ViewHolder> {

    private List<EventUpdate> updates = new ArrayList<>();

    public void submitList(List<EventUpdate> newUpdates) {
        this.updates = newUpdates != null ? newUpdates : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_update, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventUpdate update = updates.get(position);

        if (holder.tvType != null) {
            holder.tvType.setText(update.type.getDisplayName());
        }
        if (holder.tvTime != null) {
            holder.tvTime.setText(update.timestamp);
        }
        if (holder.tvMessage != null) {
            holder.tvMessage.setText(update.message);
        }
    }

    @Override
    public int getItemCount() {
        return updates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvType;
        final TextView tvTime;
        final TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_type);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }
    }
}
