package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private TextView statusText;
    private MaterialButton btnOrders, btnProducts, btnClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupClickListeners();

        // Устанавливаем начальный статус
        setStatusReady();
    }

    private void initializeViews() {
        statusText = findViewById(R.id.statusText);
        btnOrders = findViewById(R.id.btnOrders);
        btnProducts = findViewById(R.id.btnProducts);
        btnClients = findViewById(R.id.btnClients);
    }

    private void setupClickListeners() {
        btnOrders.setOnClickListener(v -> showOrders());
        btnProducts.setOnClickListener(v -> showProducts());
        btnClients.setOnClickListener(v -> showClients());
    }

    private void setStatusReady() {
        statusText.setText("✅ Приложение готово к работе");
        statusText.setBackgroundColor(getColor(R.color.color_success));
    }

    private void showOrders() {
        Toast.makeText(this,
                "Раздел 'Заказы'\n• Список заказов\n• Статусы выполнения\n• История заказов",
                Toast.LENGTH_LONG).show();
    }

    private void showProducts() {
        Toast.makeText(this,
                "Раздел 'Продукция'\n• Каталог окон\n• Цены и характеристики\n• Наличие на складе",
                Toast.LENGTH_LONG).show();
    }

    private void showClients() {
        Toast.makeText(this,
                "Раздел 'Клиенты'\n• База клиентов\n• История заказов\n• Контактная информация",
                Toast.LENGTH_LONG).show();
    }
}