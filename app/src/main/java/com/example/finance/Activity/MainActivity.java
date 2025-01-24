package com.example.finance.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.finance.MyView.CircleChartView;
import com.example.finance.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout, tabLayoutTime;
    private CircleChartView circleChartView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Установка отступов для системных панелей
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init(); // Инициализация всех R.id

        ClickInTableLayout();
        ClickInTableLayoutTime();
        DrawCircle();

        setSupportActionBar(toolbar);

        // Настройка навигации
        navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.exit);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        tabLayout = findViewById(R.id.mainTabLayout);
        tabLayoutTime = findViewById(R.id.mainTabLayoutTime);
        circleChartView = findViewById(R.id.circleChartView);
        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.NavigationLeftMenu);
        toolbar = findViewById(R.id.toolbar);
    }

    private void DrawCircle() {
        List<CircleChartView.Sector> sectors = new ArrayList<>();
        sectors.add(new CircleChartView.Sector(35f, ContextCompat.getColor(this, android.R.color.holo_red_light)));
        sectors.add(new CircleChartView.Sector(20f, ContextCompat.getColor(this, android.R.color.holo_blue_light)));
        sectors.add(new CircleChartView.Sector(15f, ContextCompat.getColor(this, android.R.color.holo_green_light)));
        sectors.add(new CircleChartView.Sector(30f, ContextCompat.getColor(this, android.R.color.holo_orange_light)));

        circleChartView.setSectors(sectors);
        circleChartView.setCenterText("35 245 ₽");
    }

    private void ClickInTableLayout() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                String tabText = tab.getText().toString();
                Toast.makeText(MainActivity.this, tabText, Toast.LENGTH_SHORT).show();
                // Здесь можно добавить логику для изменения UI
            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(@NonNull TabLayout.Tab tab) {}
        });
    }

    private void ClickInTableLayoutTime() {
        tabLayoutTime.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabText = tab.getText().toString();
                Toast.makeText(MainActivity.this, tabText, Toast.LENGTH_SHORT).show();
                // Здесь можно добавить логику для изменения UI
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        // Здесь можно добавить логику для обработки выбора пункта меню
        return true;
    }
}
