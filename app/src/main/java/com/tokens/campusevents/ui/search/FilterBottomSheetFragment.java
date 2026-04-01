package com.tokens.campusevents.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.EventCategory;

public class FilterBottomSheetFragment extends Fragment {

    private boolean isOnlineSelected = false;
    private boolean isOfflineSelected = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        NavBackStackEntry searchEntry = navController.getPreviousBackStackEntry();

        ChipGroup chipGroupCategory = view.findViewById(R.id.chip_group_category);

        // Online / Offline toggle buttons
        View btnOnline = view.findViewById(R.id.btn_online);
        View btnOffline = view.findViewById(R.id.btn_offline);

        if (btnOnline != null) {
            btnOnline.setOnClickListener(v -> {
                isOnlineSelected = !isOnlineSelected;
                isOfflineSelected = false;
                updateTypeButtonStyle(btnOnline, isOnlineSelected);
                updateTypeButtonStyle(btnOffline, false);
            });
        }

        if (btnOffline != null) {
            btnOffline.setOnClickListener(v -> {
                isOfflineSelected = !isOfflineSelected;
                isOnlineSelected = false;
                updateTypeButtonStyle(btnOffline, isOfflineSelected);
                updateTypeButtonStyle(btnOnline, false);
            });
        }

        // Apply button
        View btnApply = view.findViewById(R.id.btn_apply);
        if (btnApply != null) {
            btnApply.setOnClickListener(v -> {
                if (searchEntry == null) {
                    navController.navigateUp();
                    return;
                }

                // Category from chip group
                EventCategory selectedCategory = null;
                if (chipGroupCategory != null) {
                    int checkedId = chipGroupCategory.getCheckedChipIds().isEmpty()
                            ? View.NO_ID
                            : chipGroupCategory.getCheckedChipIds().get(0);
                    selectedCategory = chipIdToCategory(checkedId);
                }

                // Online filter
                Boolean onlineFilter = null;
                if (isOnlineSelected) onlineFilter = true;
                else if (isOfflineSelected) onlineFilter = false;

                searchEntry.getSavedStateHandle().set("filterCategory", selectedCategory);
                searchEntry.getSavedStateHandle().set("filterOnline", onlineFilter);
                searchEntry.getSavedStateHandle().set("filterFree", (Boolean) null);

                navController.navigateUp();
            });
        }

        // Clear button
        View btnClear = view.findViewById(R.id.btn_clear);
        if (btnClear != null) {
            btnClear.setOnClickListener(v -> {
                if (chipGroupCategory != null) {
                    chipGroupCategory.clearCheck();
                }
                isOnlineSelected = false;
                isOfflineSelected = false;
                if (btnOnline != null) updateTypeButtonStyle(btnOnline, false);
                if (btnOffline != null) updateTypeButtonStyle(btnOffline, false);

                if (searchEntry != null) {
                    searchEntry.getSavedStateHandle().set("filterCategory", (EventCategory) null);
                    searchEntry.getSavedStateHandle().set("filterOnline", (Boolean) null);
                    searchEntry.getSavedStateHandle().set("filterFree", (Boolean) null);
                }

                navController.navigateUp();
            });
        }
    }

    private EventCategory chipIdToCategory(int chipId) {
        if (chipId == R.id.chip_society) return EventCategory.SOCIETY;
        if (chipId == R.id.chip_tech) return EventCategory.TECH;
        if (chipId == R.id.chip_arts) return EventCategory.ARTS;
        if (chipId == R.id.chip_business) return EventCategory.BUSINESS;
        if (chipId == R.id.chip_sports) return EventCategory.SPORTS;
        return null;
    }

    private void updateTypeButtonStyle(View btn, boolean selected) {
        if (btn == null) return;
        if (selected) {
            btn.setBackgroundResource(R.drawable.bg_chip_selected);
        } else {
            btn.setBackgroundResource(R.drawable.bg_chip);
        }
    }
}
