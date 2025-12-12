package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView titleTextView;
    private Button submitButton;
    private String currentMode; // "register" или "login"

    private final SupabaseClient supabaseClient = new SupabaseClient();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        titleTextView = findViewById(R.id.title_auth);
        emailEditText = findViewById(R.id.edit_text_email);
        passwordEditText = findViewById(R.id.edit_text_password);
        submitButton = findViewById(R.id.button_submit_auth);

        currentMode = getIntent().getStringExtra("mode");

        if ("register".equals(currentMode)) {
            titleTextView.setText("Регистрация нового аккаунта");
            submitButton.setText("Зарегистрироваться");
        } else if ("login".equals(currentMode)) {
            titleTextView.setText("Вход в систему");
            submitButton.setText("Войти");
        }

        submitButton.setOnClickListener(v -> attemptAuth());
    }

    private void attemptAuth() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, введите Email и Пароль.", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.execute(() -> {
            UserSession session = null;
            String toastText;
            try {
                if ("register".equals(currentMode)) {
                    session = supabaseClient.registerUser(email, password);
                    toastText = "Регистрация успешна!";
                } else { // "login"
                    session = supabaseClient.signInUser(email, password);
                    toastText = "Вход выполнен успешно!";
                }
            } catch (Exception e) {
                e.printStackTrace();
                toastText = "Ошибка: " + e.getMessage().split(":")[0];
            }

            final UserSession finalSession = session;
            final String finalToastText = toastText;
            runOnUiThread(() -> {
                Toast.makeText(AuthActivity.this, finalToastText, Toast.LENGTH_LONG).show();
                if (finalSession != null && finalSession.isValid()) {
                    MainActivity.setCurrentUserSession(finalSession);
                    finish();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
    }
}