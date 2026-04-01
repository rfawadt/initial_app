package com.tokens.campusevents.ui.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.Notification;
import com.tokens.campusevents.data.model.NotificationType;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> notifications = new ArrayList<>();

    public void submitList(List<Notification> newNotifications) {
        this.notifications = newNotifications != null ? newNotifications : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        // Type tag
        holder.tvTypeTag.setText(notification.type.getDisplayName());

        GradientDrawable tagBg = new GradientDrawable();
        tagBg.setCornerRadius(12f);

        int bgRes;
        int textColorRes;

        switch (notification.type) {
            case UPDATE:
                bgRes = R.drawable.bg_status_live;
                textColorRes = R.color.notification_update;
                break;
            case REMINDER:
                bgRes = R.drawable.bg_status_live;
                textColorRes = R.color.notification_reminder;
                break;
            case CANCELLED:
                bgRes = R.drawable.bg_status_ended;
                textColorRes = R.color.notification_cancelled;
                break;
            default:
                bgRes = R.drawable.bg_status_live;
                textColorRes = R.color.notification_update;
                break;
        }

        holder.tvTypeTag.setBackgroundResource(bgRes);
        holder.tvTypeTag.setTextColor(ContextCompat.getColor(
                holder.itemView.getContext(), textColorRes));

        // Time
        holder.tvTime.setText(notification.timeAgo);

        // Event name
        holder.tvEventName.setText(notification.eventName);

        // Message
        holder.tvMessage.setText(notification.message);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTypeTag;
        final TextView tvTime;
        final TextView tvEventName;
        final TextView tvMessage;
        final TextView tvOptOut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTypeTag = itemView.findViewById(R.id.tv_type_tag);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvEventName = itemView.findViewById(R.id.tv_event_name);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvOptOut = itemView.findViewById(R.id.tv_opt_out);
        }
    }
}
