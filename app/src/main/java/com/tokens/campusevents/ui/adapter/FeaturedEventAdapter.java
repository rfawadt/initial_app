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
import com.tokens.campusevents.data.model.Event;
import com.tokens.campusevents.data.model.EventCategory;

import java.util.ArrayList;
import java.util.List;

public class FeaturedEventAdapter extends RecyclerView.Adapter<FeaturedEventAdapter.ViewHolder> {

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Event event);
    }

    private List<Event> events = new ArrayList<>();
    private final OnEventClickListener eventClickListener;
    private final OnFavoriteClickListener favoriteClickListener;

    public FeaturedEventAdapter(OnEventClickListener eventClickListener,
                                OnFavoriteClickListener favoriteClickListener) {
        this.eventClickListener = eventClickListener;
        this.favoriteClickListener = favoriteClickListener;
    }

    public void submitList(List<Event> newEvents) {
        this.events = newEvents != null ? newEvents : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_featured, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events.get(position);

        // Category tag
        holder.tvCategory.setText(event.category.getDisplayName().toUpperCase());
        GradientDrawable categoryBg = new GradientDrawable();
        categoryBg.setCornerRadius(12f);
        categoryBg.setColor(ContextCompat.getColor(holder.itemView.getContext(),
                getCategoryColor(event.category)));
        holder.tvCategory.setBackground(categoryBg);

        // New tag visibility (show for events with high capacity remaining)
        holder.tvNewTag.setVisibility(View.VISIBLE);

        // Title, date, venue
        holder.tvTitle.setText(event.title);
        holder.tvDate.setText(event.date);
        holder.tvVenue.setText(event.venue);

        // Price
        if (event.price == 0) {
            holder.tvPrice.setText("Free");
            holder.tvPrice.setTextColor(ContextCompat.getColor(
                    holder.itemView.getContext(), R.color.free_tag));
        } else {
            holder.tvPrice.setText("PKR " + event.price);
            holder.tvPrice.setTextColor(ContextCompat.getColor(
                    holder.itemView.getContext(), R.color.price_tag));
        }

        // Image
        if (event.imageRes != 0) {
            holder.ivImage.setImageResource(event.imageRes);
        }

        // Favorite button
        holder.btnFavorite.setOnClickListener(v -> {
            if (favoriteClickListener != null) {
                favoriteClickListener.onFavoriteClick(event);
            }
        });

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

    private int getCategoryColor(EventCategory category) {
        switch (category) {
            case SOCIETY:
                return R.color.category_society;
            case ARTS:
                return R.color.category_arts;
            case TECH:
                return R.color.category_tech;
            case BUSINESS:
                return R.color.category_business;
            case SPORTS:
                return R.color.category_sports;
            case ACADEMIC:
                return R.color.category_academic;
            default:
                return R.color.category_society;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivImage;
        final TextView tvCategory;
        final TextView tvNewTag;
        final ImageView btnFavorite;
        final TextView tvTitle;
        final TextView tvDate;
        final TextView tvVenue;
        final TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvNewTag = itemView.findViewById(R.id.tv_new_tag);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvVenue = itemView.findViewById(R.id.tv_venue);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
