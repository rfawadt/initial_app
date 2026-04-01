package com.tokens.campusevents.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tokens.campusevents.R;

public class FilterBottomSheetFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        View btnApply = view.findViewById(R.id.btn_apply);
        View btnClear = view.findViewById(R.id.btn_clear);

        if (btnApply != null) {
            btnApply.setOnClickListener(v -> navController.navigateUp());
        }

        if (btnClear != null) {
            btnClear.setOnClickListener(v -> navController.navigateUp());
        }
    }
}
