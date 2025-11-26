package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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

        // Показываем email пользователя
        String userEmail = AuthHelper.getUserEmail(this);
        tvWelcome.setText("Система судмедэкспертизы\nДобро пожаловать, " + userEmail + "!");
    }

    private void initializeViews() {
        btnCases = findViewById(R.id.btnCases);
        btnNewCase = findViewById(R.id.btnNewCase);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);
    }

    private void setupClickListeners() {
        btnCases.setOnClickListener(v -> showToast("Переход к списку дел"));
        btnNewCase.setOnClickListener(v -> showToast("Создание нового дела"));
        btnProfile.setOnClickListener(v -> showToast("Профиль пользователя"));
        btnLogout.setOnClickListener(v -> logout());
    }

    private void showToast(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        // ОЧИСТКА СТАТУСА АВТОРИЗАЦИИ
        AuthHelper.logout(DashboardActivity.this);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}