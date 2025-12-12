package com.example.myapplication;

public class UserSession {
    public String access_token;
    public String refresh_token;
    public User user;

    public static class User {
        public String id;
        public String email;
    }

    public boolean isValid() {
        return access_token != null && !access_token.isEmpty();
    }

    @Override
    public String toString() {
        if (isValid()) {
            return "ID: " + user.id + ", Email: " + user.email + ", Токен: " + access_token.substring(0, 10) + "...";
        }
        return "Сессия не активна.";
    }
}