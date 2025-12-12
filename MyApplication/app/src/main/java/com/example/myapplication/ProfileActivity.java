package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button saveButton;

    private final SupabaseClient supabaseClient = new SupabaseClient();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private UserSession currentSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentSession = MainActivity.currentUserSession;
        if (currentSession == null || !currentSession.isValid()) {
            Toast.makeText(this, "Сначала войдите в систему!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        emailEditText = findViewById(R.id.edit_text_email_profile);
        usernameEditText = findViewById(R.id.edit_text_username_profile);
        firstNameEditText = findViewById(R.id.edit_text_first_name_profile);
        lastNameEditText = findViewById(R.id.edit_text_last_name_profile);
        saveButton = findViewById(R.id.button_save_profile);

        emailEditText.setText(currentSession.user.email);
        emailEditText.setEnabled(false);

        fetchProfileData();

        saveButton.setOnClickListener(v -> saveProfileData());
    }

    private void fetchProfileData() {
        executorService.execute(() -> {
            String toastText;
            try {
                Profile profile = supabaseClient.fetchProfile(currentSession);
                runOnUiThread(() -> {
                    usernameEditText.setText(profile.username);
                    firstNameEditText.setText(profile.first_name);
                    lastNameEditText.setText(profile.last_name);
                    // Toast.makeText(ProfileActivity.this, "Данные профиля загружены.", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                toastText = "Ошибка загрузки профиля: " + e.getMessage();
                final String finalToastText = toastText;
                runOnUiThread(() -> Toast.makeText(ProfileActivity.this, finalToastText, Toast.LENGTH_LONG).show());
            }
        });
    }

    private void saveProfileData() {
        Profile updatedProfile = new Profile();
        updatedProfile.username = usernameEditText.getText().toString();
        updatedProfile.first_name = firstNameEditText.getText().toString();
        updatedProfile.last_name = lastNameEditText.getText().toString();

        executorService.execute(() -> {
            String toastText;
            try {
                supabaseClient.updateProfile(currentSession, updatedProfile);
                toastText = "Профиль успешно обновлен!";
            } catch (Exception e) {
                e.printStackTrace();
                toastText = "Ошибка сохранения: " + e.getMessage();
            }

            final String finalToastText = toastText;
            runOnUiThread(() -> Toast.makeText(ProfileActivity.this, finalToastText, Toast.LENGTH_LONG).show());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
    }
}