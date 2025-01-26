package com.example.finance.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import com.example.finance.Activity.Login.LoginActivity;
import com.example.finance.Activity.Login.RegisterActivity;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.MyView.CircleChartView;
import com.example.finance.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout _tabLayout, _tabLayoutTime;
    private CircleChartView _circleChartView;
    private DrawerLayout _drawerLayout;
    private NavigationView _navigationView;
    private Toolbar _toolbar;
    private String _wasteOrIncome = "Расходы";
    private SPUser _userSP;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init(); // Инициализация всех R.id

        ClickInTableLayout();
        ClickInTableLayoutTime();
        DrawCircle();

        setSupportActionBar(_toolbar);

        // Настройка навигации
        _navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, _drawerLayout, _toolbar, R.string.open, R.string.exit);
        _drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        _navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        _tabLayout = findViewById(R.id.mainTabLayout);
        _tabLayoutTime = findViewById(R.id.mainTabLayoutTime);
        _circleChartView = findViewById(R.id.circleChartView);
        _drawerLayout = findViewById(R.id.main);
        _navigationView = findViewById(R.id.NavigationLeftMenu);
        _toolbar = findViewById(R.id.toolbar);

        _userSP = new SPUser(this);
    }

    private void DrawCircle() {
        List<CircleChartView.Sector> sectors = new ArrayList<>();
        sectors.add(new CircleChartView.Sector(35f, ContextCompat.getColor(this, android.R.color.holo_red_light)));
        sectors.add(new CircleChartView.Sector(20f, ContextCompat.getColor(this, android.R.color.holo_blue_light)));
        sectors.add(new CircleChartView.Sector(15f, ContextCompat.getColor(this, android.R.color.holo_green_light)));
        sectors.add(new CircleChartView.Sector(30f, ContextCompat.getColor(this, android.R.color.holo_orange_light)));

        _circleChartView.setSectors(sectors);
        _circleChartView.setCenterText("35 245 ₽");
    }

    private void ClickInTableLayout() {
        _tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                String tabText = tab.getText().toString();
                _wasteOrIncome = tabText;
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
        _tabLayoutTime.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        _drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.MenuNavigatorProfile){

        }
        else if(item.getItemId() == R.id.MenuNavigatorSettings){

        }
        else if(item.getItemId() == R.id.MenuNavigatorFindFriends){

        }
        else if(item.getItemId() == R.id.MenuNavigatorMyFriends){

        }
        else if(item.getItemId() == R.id.MenuNavigatorExit){
            try {
                _userSP.delete();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } finally {

            }
        }

        return true;
    }

    public void DeleteAfter(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onClickToCreateWaste(View v){
        Intent intent = new Intent(this, AddWasteAndIncomeActivity.class)
                .putExtra("WasteOrIncome",_wasteOrIncome);
        startActivity(intent);
    }
}
