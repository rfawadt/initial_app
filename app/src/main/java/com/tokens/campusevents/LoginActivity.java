package com.tokens.campusevents;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.tokens.campusevents.data.repository.UserRepository;

public class LoginActivity extends AppCompatActivity {

    private boolean isStudentSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView btnStudent = findViewById(R.id.btn_student);
        TextView btnOrganizer = findViewById(R.id.btn_organizer);
        TextView btnSignIn = findViewById(R.id.btn_sign_in);
        EditText etEmail = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);

        btnStudent.setOnClickListener(v -> {
            isStudentSelected = true;
            btnStudent.setBackgroundResource(R.drawable.bg_chip_selected);
            btnStudent.setTextColor(ContextCompat.getColor(this, R.color.text_on_primary));
            btnOrganizer.setBackgroundResource(R.drawable.bg_chip);
            btnOrganizer.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        });

        btnOrganizer.setOnClickListener(v -> {
            isStudentSelected = false;
            btnOrganizer.setBackgroundResource(R.drawable.bg_chip_selected);
            btnOrganizer.setTextColor(ContextCompat.getColor(this, R.color.text_on_primary));
            btnStudent.setBackgroundResource(R.drawable.bg_chip);
            btnStudent.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        });

        btnSignIn.setOnClickListener(v -> {
            UserRepository.getInstance().setOrganizerMode(!isStudentSelected);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
