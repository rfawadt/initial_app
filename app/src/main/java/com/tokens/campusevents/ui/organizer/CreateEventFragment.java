package com.tokens.campusevents.ui.organizer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tokens.campusevents.R;
import com.tokens.campusevents.data.model.EventCategory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateEventFragment extends Fragment {

    private CreateEventViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CreateEventViewModel.class);
        NavController navController = Navigation.findNavController(view);

        // Find views
        View btnBack = view.findViewById(R.id.btn_back);
        EditText etEventName = view.findViewById(R.id.et_event_name);
        EditText etDescription = view.findViewById(R.id.et_description);
        Spinner spinnerCategory = view.findViewById(R.id.spinner_category);
        EditText etVenue = view.findViewById(R.id.et_venue);
        EditText etStartDate = view.findViewById(R.id.et_start_date);
        EditText etStartTime = view.findViewById(R.id.et_start_time);
        EditText etCapacity = view.findViewById(R.id.et_capacity);
        EditText etPrice = view.findViewById(R.id.et_price);
        SwitchMaterial switchFree = view.findViewById(R.id.switch_free);
        View btnSaveDraft = view.findViewById(R.id.btn_save_draft);
        View btnPublish = view.findViewById(R.id.btn_publish);

        // Back button
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> navController.navigateUp());
        }

        // Category spinner - exclude ALL
        List<String> categoryNames = new ArrayList<>();
        for (EventCategory cat : EventCategory.values()) {
            if (cat != EventCategory.ALL) {
                categoryNames.add(cat.getDisplayName());
            }
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, categoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerCategory != null) {
            spinnerCategory.setAdapter(spinnerAdapter);
        }

        // Date picker
        if (etStartDate != null) {
            etStartDate.setFocusable(false);
            etStartDate.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                        (picker, year, month, dayOfMonth) -> {
                            String dateStr = String.format(Locale.getDefault(),
                                    "%s %d, %d", getMonthName(month), dayOfMonth, year);
                            etStartDate.setText(dateStr);
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            });
        }

        // Time picker
        if (etStartTime != null) {
            etStartTime.setFocusable(false);
            etStartTime.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                        (picker, hourOfDay, minute) -> {
                            String amPm = hourOfDay >= 12 ? "PM" : "AM";
                            int hour12 = hourOfDay == 0 ? 12 : (hourOfDay > 12 ? hourOfDay - 12 : hourOfDay);
                            String timeStr = String.format(Locale.getDefault(),
                                    "%d:%02d %s", hour12, minute, amPm);
                            etStartTime.setText(timeStr);
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false);
                timePickerDialog.show();
            });
        }

        // Free switch
        if (switchFree != null && etPrice != null) {
            switchFree.setOnCheckedChangeListener((buttonView, isChecked) -> {
                etPrice.setEnabled(!isChecked);
                if (isChecked) {
                    etPrice.setText("0");
                }
            });
        }

        // Save draft
        if (btnSaveDraft != null) {
            btnSaveDraft.setOnClickListener(v -> {
                if (validateFields(etEventName, etVenue, etCapacity)) {
                    createEvent(etEventName, etDescription, spinnerCategory, etVenue,
                            etStartDate, etStartTime, etCapacity, etPrice, switchFree, true);
                }
            });
        }

        // Publish
        if (btnPublish != null) {
            btnPublish.setOnClickListener(v -> {
                if (validateFields(etEventName, etVenue, etCapacity)) {
                    createEvent(etEventName, etDescription, spinnerCategory, etVenue,
                            etStartDate, etStartTime, etCapacity, etPrice, switchFree, false);
                }
            });
        }

        // Observe event created
        viewModel.getEventCreated().observe(getViewLifecycleOwner(), created -> {
            if (created != null && created) {
                Toast.makeText(requireContext(), "Event created successfully!",
                        Toast.LENGTH_SHORT).show();
                navController.navigateUp();
            }
        });
    }

    private boolean validateFields(EditText etName, EditText etVenue, EditText etCapacity) {
        if (etName == null || etName.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Please enter event name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etVenue == null || etVenue.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Please enter venue", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etCapacity == null || etCapacity.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Please enter capacity", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createEvent(EditText etName, EditText etDescription, Spinner spinnerCategory,
                             EditText etVenue, EditText etDate, EditText etTime,
                             EditText etCapacity, EditText etPrice, SwitchMaterial switchFree,
                             boolean isDraft) {
        String title = etName.getText().toString().trim();
        String description = etDescription != null ? etDescription.getText().toString().trim() : "";
        String category = spinnerCategory != null && spinnerCategory.getSelectedItem() != null
                ? spinnerCategory.getSelectedItem().toString() : "Society";
        String venue = etVenue.getText().toString().trim();
        String date = etDate != null ? etDate.getText().toString().trim() : "";
        String time = etTime != null ? etTime.getText().toString().trim() : "";
        int capacity = 0;
        try {
            capacity = Integer.parseInt(etCapacity.getText().toString().trim());
        } catch (NumberFormatException e) {
            // Use default 0
        }
        int price = 0;
        if (switchFree != null && !switchFree.isChecked() && etPrice != null) {
            try {
                price = Integer.parseInt(etPrice.getText().toString().trim());
            } catch (NumberFormatException e) {
                // Use default 0
            }
        }

        viewModel.createEvent(title, description, category, venue, date, time, capacity, price, isDraft);
    }

    private String getMonthName(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return months[month];
    }
}
