package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken; // Нужен для парсинга массива Profile
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;

import java.io.IOException;

public class SupabaseClient {
    private static final String SUPABASE_URL = "jyqtvoxgfzalecqxddqt.supabase.co";
    private static final String SUPABASE_ANON_KEY = "sb_publishable_VeI2kNodgI2O-Di88XfXHQ__MpYKyBr";

    private static final String PROFILE_TABLE_NAME = "profiles";

    private static final String AUTH_SIGNUP_URL = SUPABASE_URL + "/auth/v1/signup";
    private static final String AUTH_SIGNIN_URL = SUPABASE_URL + "/auth/v1/token?grant_type=password";

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public UserSession registerUser(String email, String password) throws IOException {
        String jsonBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        RequestBody body = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(AUTH_SIGNUP_URL)
                .addHeader("apikey", SUPABASE_ANON_KEY)
                .addHeader("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                .post(body)
                .build();

        return executeAuthRequest(request);
    }

    public UserSession signInUser(String email, String password) throws IOException {
        String jsonBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        RequestBody body = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(AUTH_SIGNIN_URL)
                .addHeader("apikey", SUPABASE_ANON_KEY)
                .addHeader("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                .post(body)
                .build();

        return executeAuthRequest(request);
    }

    private UserSession executeAuthRequest(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();

            if (response.isSuccessful()) {
                return gson.fromJson(responseBody, UserSession.class);
            } else {
                throw new IOException("Auth Failed (" + response.code() + "): " + responseBody);
            }
        }
    }


    public Profile fetchProfile(UserSession userSession) throws IOException {
        String profileId = userSession.user.id;
        String url = SUPABASE_URL + "/rest/v1/" + PROFILE_TABLE_NAME + "?select=*&id=eq." + profileId;
        String token = userSession.access_token;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", SUPABASE_ANON_KEY)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Profile fetch failed: " + response.code() + " " + response.body().string());
            }
            String jsonResponse = response.body().string();
            Profile[] profiles = gson.fromJson(jsonResponse, Profile[].class);

            if (profiles.length > 0) {
                return profiles[0];
            } else {
                throw new IOException("Profile not found.");
            }
        }
    }

    public void updateProfile(UserSession userSession, Profile newProfile) throws IOException {
        String profileId = userSession.user.id;
        String url = SUPABASE_URL + "/rest/v1/" + PROFILE_TABLE_NAME + "?id=eq." + profileId;
        String token = userSession.access_token;

        String jsonBody = String.format("{\"username\": \"%s\", \"first_name\": \"%s\", \"last_name\": \"%s\"}",
                newProfile.username, newProfile.first_name, newProfile.last_name);

        RequestBody body = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", SUPABASE_ANON_KEY)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Prefer", "return=minimal")
                .patch(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Profile update failed: " + response.code() + " " + response.body().string());
            }

        }
    }
}