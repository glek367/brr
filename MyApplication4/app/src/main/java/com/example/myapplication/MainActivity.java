package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button profileButton, logoutButton;
    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupClickListeners();

        welcomeTextView.setText("Добро пожаловать в 'Я танцую!'");
    }

    private void initializeViews() {
        profileButton = findViewById(R.id.profileButton);
        logoutButton = findViewById(R.id.logoutButton);
        welcomeTextView = findViewById(R.id.welcomeTextView);
    }

    private void setupClickListeners() {
        profileButton.setOnClickListener(v -> navigateToProfile());
        logoutButton.setOnClickListener(v -> logout());
    }

    private void navigateToProfile() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    private void logout() {
        // В реальном приложении здесь будет выход из Supabase
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}