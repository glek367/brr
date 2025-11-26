package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnCases, btnNewCase, btnProfile, btnLogout;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeViews();
        setupClickListeners();

        tvWelcome.setText("Добро пожаловать в систему судмедэкспертизы");
    }

    private void initializeViews() {
        btnCases = findViewById(R.id.btnCases);
        btnNewCase = findViewById(R.id.btnNewCase);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);
    }

    private void setupClickListeners() {
        btnCases.setOnClickListener(v -> showCases());
        btnNewCase.setOnClickListener(v -> createNewCase());
        btnProfile.setOnClickListener(v -> showProfile());
        btnLogout.setOnClickListener(v -> logout());
    }

    private void showCases() {
        Toast.makeText(this, "Список дел", Toast.LENGTH_SHORT).show();
        // Здесь будет переход к списку дел
    }

    private void createNewCase() {
        Toast.makeText(this, "Создание нового дела", Toast.LENGTH_SHORT).show();
        // Здесь будет создание нового дела
    }

    private void showProfile() {
        Toast.makeText(this, "Профиль пользователя", Toast.LENGTH_SHORT).show();
        // Здесь будет просмотр профиля
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}