package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView btnMenu;
    private TextView btnCart;
    private TextView tvSectionTitle;
    private TextView btnSearch;
    private LinearLayout searchLayout;
    private EditText etSearch;
    private TextView btnClearSearch;

    private List<Product> fullProductList; // полный список товаров
    private ProductAdapter adapter; // адаптер для RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов
        drawerLayout = findViewById(R.id.drawerLayout);
        btnMenu = findViewById(R.id.btnMenu);
        btnCart = findViewById(R.id.btnCart);
        tvSectionTitle = findViewById(R.id.tvSectionTitle);
        btnSearch = findViewById(R.id.btnSearch);
        searchLayout = findViewById(R.id.searchLayout);
        etSearch = findViewById(R.id.etSearch);
        btnClearSearch = findViewById(R.id.btnClearSearch);

        // Бургер меню
        btnMenu.setOnClickListener(v -> drawerLayout.open());

        // Корзина
        btnCart.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Корзина пока пуста", Toast.LENGTH_SHORT).show());

        // ========== СОЗДАЕМ СПИСОК ТОВАРОВ ==========
        fullProductList = new ArrayList<>();
        fullProductList.add(new Product("Молоко", "1 литр, 3.2%", 85, "🥛"));
        fullProductList.add(new Product("Хлеб", "Бородинский, 300 г", 54, "🍞"));
        fullProductList.add(new Product("Яйца", "10 шт, отборные", 129, "🥚"));
        fullProductList.add(new Product("Сыр", "Российский, 200 г", 150, "🧀"));
        fullProductList.add(new Product("Колбаса", "Докторская, 400 г", 320, "🥩"));
        fullProductList.add(new Product("Яблоки", "1 кг, красные", 99, "🍎"));
        fullProductList.add(new Product("Сок", "Апельсиновый, 1 л", 120, "🧃"));
        fullProductList.add(new Product("Йогурт", "Питьевой, 300 г", 65, "🥛"));
        fullProductList.add(new Product("Печенье", "Овсяное, 200 г", 89, "🍪"));

        // ========== НАСТРАИВАЕМ RECYCLERVIEW ==========
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductAdapter(new ArrayList<>(fullProductList), this);
        recyclerView.setAdapter(adapter);

        // ========== ЛОГИКА ПОИСКА ==========
        btnSearch.setOnClickListener(v -> {
            // Показываем поле поиска, скрываем заголовок и иконку
            tvSectionTitle.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            etSearch.requestFocus();
            // Показываем клавиатуру
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
        });

        btnClearSearch.setOnClickListener(v -> {
            etSearch.setText(""); // очищаем поле
            closeSearch(); // закрываем режим поиска
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // ========== ПУНКТЫ МЕНЮ ==========
        LinearLayout menuCatalog = findViewById(R.id.menuCatalog);
        LinearLayout menuSales = findViewById(R.id.menuSales);
        LinearLayout menuCart = findViewById(R.id.menuCart);
        LinearLayout menuContacts = findViewById(R.id.menuContacts);

        menuCatalog.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Каталог", Toast.LENGTH_SHORT).show();
            drawerLayout.close();
        });

        menuSales.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Акции", Toast.LENGTH_SHORT).show();
            drawerLayout.close();
        });

        menuCart.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Корзина", Toast.LENGTH_SHORT).show();
            drawerLayout.close();
        });

        menuContacts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(intent);
            drawerLayout.close();
        });
    }

    // Фильтрация товаров по введённому тексту
    private void filterProducts(String query) {
        List<Product> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            filteredList.addAll(fullProductList);
        } else {
            for (Product product : fullProductList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }
        }
        adapter.updateList(filteredList);
    }

    // Закрыть режим поиска и вернуть заголовок
    private void closeSearch() {
        searchLayout.setVisibility(View.GONE);
        tvSectionTitle.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.VISIBLE);
        // Скрыть клавиатуру
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        // Сбросить фильтр
        filterProducts("");
    }

    // Переопределяем кнопку "Назад", чтобы закрыть поиск, если он открыт
    @Override
    public void onBackPressed() {
        if (searchLayout.getVisibility() == View.VISIBLE) {
            closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}