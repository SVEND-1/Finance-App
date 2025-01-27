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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.Activity.Login.LoginActivity;
import com.example.finance.Activity.Login.RegisterActivity;
import com.example.finance.Adapter.AdapterIncome;
import com.example.finance.Adapter.AdapterWaste;
import com.example.finance.Data.DataBase.DBUser;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Model.Income;
import com.example.finance.Model.User;
import com.example.finance.Model.Waste;
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
    private DBUser _userDAO;
    private RecyclerView _recyclerView;
    private List<Income> _incomeList;
    private List<Waste> _wasteList;

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

        //Получение пользователя и заполнение лист
        getUserFromDataBase();

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

        //СПИСОК
        createRecycleView();
    }

    private void getUserFromDataBase(){
        String login = _userSP.getUserLogin();
        _userDAO.isEmptyUser (login, new DBUser .UserCallback() {
            @Override
            public void onCallback(User user) {
                if (user != null) {
                   _incomeList = user.get_listIncome();
                   _wasteList = user.get_listWaste();
                }
            }
        });
    }

    private void init() {
        _tabLayout = findViewById(R.id.mainTabLayout);
        _tabLayoutTime = findViewById(R.id.mainTabLayoutTime);
        _circleChartView = findViewById(R.id.circleChartView);
        _drawerLayout = findViewById(R.id.main);
        _navigationView = findViewById(R.id.NavigationLeftMenu);
        _toolbar = findViewById(R.id.toolbar);
        _recyclerView = findViewById(R.id.mainRecycle);


        _userDAO = new DBUser();
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

    private void createRecycleView(){
//        if(_wasteOrIncome.equals("Расходы")){
//            ArrayList<Waste> wastes = new ArrayList<>(_wasteList);
//            Waste waste = new Waste(1,"da","das","das");
//            Waste waste2 = new Waste(132,"da","das","das");
//            wastes.add(waste);
//            wastes.add(waste2);
//
//            AdapterWaste adapterWaste = new AdapterWaste(wastes);
//            _recyclerView.setHasFixedSize(true);
//            _recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            _recyclerView.setAdapter(adapterWaste);
//        }
//        else if (_wasteOrIncome.equals("Доходы")) {
//            ArrayList<Income> incomes = new ArrayList<>(_incomeList);
//            Income income = new Income(132,"da","das","das");
//            Income income2 = new Income(1322,"da","das","das");
//            incomes.add(income);
//            incomes.add(income2);
//
//            AdapterIncome adapterIncome = new AdapterIncome(incomes);
//            _recyclerView.setHasFixedSize(true);
//            _recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            _recyclerView.setAdapter(adapterIncome);
//        }
    }

    private void ClickInTableLayout() {
        _tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                String tabText = tab.getText().toString();
                _wasteOrIncome = tabText;
                createRecycleView();
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
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
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
                finish();
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
