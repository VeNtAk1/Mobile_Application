package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView btnMenu;
    private TextView btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация
        drawerLayout = findViewById(R.id.drawerLayout);
        btnMenu = findViewById(R.id.btnMenu);
        btnCart = findViewById(R.id.btnCart);

        // Бургер меню
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        // Корзина
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Корзина пока пуста", Toast.LENGTH_SHORT).show();
            }
        });

        // Кнопка фильтра
        TextView btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Фильтр (в разработке)", Toast.LENGTH_SHORT).show();
            }
        });

        // ========== СОЗДАЕМ СПИСОК ТОВАРОВ ==========
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Молоко", "1 литр, 3.2%", 85, "🥛"));
        productList.add(new Product("Хлеб", "Бородинский, 300 г", 54, "🍞"));
        productList.add(new Product("Яйца", "10 шт, отборные", 129, "🥚"));
        productList.add(new Product("Сыр", "Российский, 200 г", 150, "🧀"));
        productList.add(new Product("Колбаса", "Докторская, 400 г", 320, "🥩"));
        productList.add(new Product("Яблоки", "1 кг, красные", 99, "🍎"));
        productList.add(new Product("Сок", "Апельсиновый, 1 л", 120, "🧃"));
        productList.add(new Product("Йогурт", "Питьевой, 300 г", 65, "🥛"));
        productList.add(new Product("Печенье", "Овсяное, 200 г", 89, "🍪"));
        productList.add(new Product("Xiaomi Redmi note 9 pro", "Телефон", 89, "mobile"));

        // ========== НАСТРАИВАЕМ RECYCLERVIEW ==========
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter adapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(adapter);

        // ========== ПУНКТЫ МЕНЮ ==========
        LinearLayout menuCatalog = findViewById(R.id.menuCatalog);
        LinearLayout menuSales = findViewById(R.id.menuSales);
        LinearLayout menuCart = findViewById(R.id.menuCart);
        LinearLayout menuContacts = findViewById(R.id.menuContacts);

        menuCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Каталог", Toast.LENGTH_SHORT).show();
                drawerLayout.close();
            }
        });

        menuSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Акции", Toast.LENGTH_SHORT).show();
                drawerLayout.close();
            }
        });

        menuCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Корзина", Toast.LENGTH_SHORT).show();
                drawerLayout.close();
            }
        });

        menuContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent);
                drawerLayout.close(); // закрываем меню после перехода
            }
        });
    }
}