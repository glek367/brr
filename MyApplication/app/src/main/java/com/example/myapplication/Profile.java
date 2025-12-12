package com.example.myapplication;

public class Profile {
    public String id;
    public String username;
    public String first_name;
    public String last_name;
    public String created_at; // Дата создания

    // Вспомогательный метод для проверки
    public boolean isValid() {
        return id != null && !id.isEmpty();
    }
}