package com.example.myapplication;

// Альтернативные импорты
import io.github.jan.supabase.SupabaseClient;
import io.github.jan.supabase.SupabaseClientBuilder;

public class SupabaseManager {

    private static final String SUPABASE_URL = "https://hvkgvnfyathzjlzkwdba.supabase.co";
    private static final String SUPABASE_KEY = "sb_publishable_Tuf2xpYL8-FN9_O8-EdBwA_7F5dtIyl";

    private static SupabaseClient client;

    public static SupabaseClient getClient() {
        if (client == null) {
            try {
                client = INSTANCE.create(SUPABASE_URL, SUPABASE_KEY);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize Supabase client");
            }
        }
        return client;
    }

    private static class INSTANCE {
        public static SupabaseClient create(String supabaseUrl, String supabaseKey) {

            return null;
        }
}
}