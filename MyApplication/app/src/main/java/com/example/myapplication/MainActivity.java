package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private TextView sessionTextView;

    static UserSession currentUserSession = null;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        sessionTextView = findViewById(R.id.sessionTextView);

        setupMenuButtons();
        resultTextView.setText("Функционал каталога отключен. Доступна только Auth и Профиль.");
    }

    private void setupMenuButtons() {
        Button btnCatalog = findViewById(R.id.btn_catalog);
        Button btnRegister = findViewById(R.id.btn_register);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnProfile = findViewById(R.id.btn_profile); // Кнопка для перехода в профиль

        btnCatalog.setOnClickListener(v -> resultTextView.setText("Каталог пока не реализован."));

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AuthActivity.class).putExtra("mode", "register"));
        });

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AuthActivity.class).putExtra("mode", "login"));
        });

        btnProfile.setOnClickListener(v -> {
            if (currentUserSession != null && currentUserSession.isValid()) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else {
                Toast.makeText(MainActivity.this, "Пожалуйста, войдите в систему!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void setCurrentUserSession(UserSession session) {
        currentUserSession = session;
    }

    private void updateSessionUI() {
        if (currentUserSession != null && currentUserSession.isValid()) {
            sessionTextView.setText("Статус: Активен. Пользователь: " + currentUserSession.user.email);
            sessionTextView.setBackgroundResource(android.R.color.holo_green_light);
        } else {
            sessionTextView.setText("Статус: Нет активной сессии. Войдите или зарегистрируйтесь.");
            sessionTextView.setBackgroundResource(android.R.color.holo_red_light);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSessionUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
    }
}