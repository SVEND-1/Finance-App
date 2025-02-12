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
import com.example.finance.Data.DataBase.DBIncome;
import com.example.finance.Data.DataBase.DBUser;
import com.example.finance.Data.DataBase.DBWaste;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Model.Income;
import com.example.finance.Model.User;
import com.example.finance.Model.Waste;
import com.example.finance.MyView.CircleChartView;
import com.example.finance.R;
import com.example.finance.WasteActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout _tabLayout, _tabLayoutTime;
    private CircleChartView _circleChartView;
    private DrawerLayout _drawerLayout;
    private NavigationView _navigationView;
    private Toolbar _toolbar;
    private String _wasteOrIncome = "Расходы",period = "День";
    private SPUser _userSP;
    private DBUser _userDAO;
    private DBWaste _wasteDAO;
    private DBIncome _dbIncome;
    private RecyclerView _recyclerView;
    private List<Income> _incomeList;
    private List<Waste> _wasteList;
    private AdapterWaste _adapterWaste;
    private AdapterIncome _adapterIncome;

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
        DrawCircleWaste();

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

    private void init() {
        _tabLayout = findViewById(R.id.mainTabLayout);
        _tabLayoutTime = findViewById(R.id.mainTabLayoutTime);
        _circleChartView = findViewById(R.id.circleChartView);
        _drawerLayout = findViewById(R.id.main);
        _navigationView = findViewById(R.id.NavigationLeftMenu);
        _toolbar = findViewById(R.id.toolbar);
        _recyclerView = findViewById(R.id.mainRecycle);

        _userDAO = new DBUser();
        _wasteDAO = new DBWaste();
        _dbIncome = new DBIncome();
        _userSP = new SPUser(this);
        _dbIncome = new DBIncome();

        _wasteList = new ArrayList<>();
        _incomeList = new ArrayList<>();

        _adapterWaste = new AdapterWaste(_wasteList);
        _adapterIncome = new AdapterIncome(_incomeList);

        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void DrawCircleWaste() {
        List<CircleChartView.Sector> sectors = new ArrayList<>();
        HashMap<String, Float> wastePercentageOfTheColorInCircle = wasteCircle();

        if (wastePercentageOfTheColorInCircle != null) {
            addSectorIfNotNull(sectors, "Образование", wastePercentageOfTheColorInCircle.get("Образование"),
                    ContextCompat.getColor(this, android.R.color.holo_red_light));
            addSectorIfNotNull(sectors, "Продукты", wastePercentageOfTheColorInCircle.get("Продукты"),
                    ContextCompat.getColor(this, android.R.color.holo_blue_light));
            addSectorIfNotNull(sectors, "Одежда", wastePercentageOfTheColorInCircle.get("Одежда"),
                    ContextCompat.getColor(this, android.R.color.holo_green_light));
            addSectorIfNotNull(sectors, "Больница", wastePercentageOfTheColorInCircle.get("Больница"),
                    ContextCompat.getColor(this, android.R.color.holo_orange_light));
            addSectorIfNotNull(sectors, "Спорт", wastePercentageOfTheColorInCircle.get("Спорт"),
                    ContextCompat.getColor(this, android.R.color.black));
            addSectorIfNotNull(sectors, "Транспорт", wastePercentageOfTheColorInCircle.get("Транспорт"),
                    ContextCompat.getColor(this, android.R.color.darker_gray));
            addSectorIfNotNull(sectors, "Досуг", wastePercentageOfTheColorInCircle.get("Досуг"),
                    ContextCompat.getColor(this, android.R.color.system_on_error_dark));
            addSectorIfNotNull(sectors, "Другое", wastePercentageOfTheColorInCircle.get("Другое"),
                    ContextCompat.getColor(this, android.R.color.system_accent1_0));

            _circleChartView.setSectors(sectors);
            _circleChartView.setCenterText(String.valueOf(wastePercentageOfTheColorInCircle.get("Сумма")));
        }
    }

    private void DrawCircleIncome() {
        List<CircleChartView.Sector> sectors = new ArrayList<>();
        HashMap<String, Float> incomePercentageOfTheColorInCircle = incomeCircle();

        if (incomePercentageOfTheColorInCircle != null) {
            addSectorIfNotNull(sectors, "Зарплата", incomePercentageOfTheColorInCircle.get("Зарплата"),
                    ContextCompat.getColor(this, android.R.color.holo_red_light));
            addSectorIfNotNull(sectors, "Подарки", incomePercentageOfTheColorInCircle.get("Подарки"),
                    ContextCompat.getColor(this, android.R.color.holo_blue_light));
            addSectorIfNotNull(sectors, "Проценты банка", incomePercentageOfTheColorInCircle.get("Проценты банка"),
                    ContextCompat.getColor(this, android.R.color.holo_green_light));
            addSectorIfNotNull(sectors, "Гос. выплаты", incomePercentageOfTheColorInCircle.get("Гос. выплаты"),
                    ContextCompat.getColor(this, android.R.color.holo_orange_light));
            addSectorIfNotNull(sectors, "Акции", incomePercentageOfTheColorInCircle.get("Акции"),
                    ContextCompat.getColor(this, android.R.color.black));
            addSectorIfNotNull(sectors, "Ценные бумаги", incomePercentageOfTheColorInCircle.get("Ценные бумаги"),
                    ContextCompat.getColor(this, android.R.color.darker_gray));
            addSectorIfNotNull(sectors, "Продажа", incomePercentageOfTheColorInCircle.get("Продажа"),
                    ContextCompat.getColor(this, android.R.color.system_on_error_dark));
            addSectorIfNotNull(sectors, "Другое", incomePercentageOfTheColorInCircle.get("Другое"),
                    ContextCompat.getColor(this, android.R.color.system_accent1_0));

            _circleChartView.setSectors(sectors);
            _circleChartView.setCenterText(String.valueOf(incomePercentageOfTheColorInCircle.get("Сумма")));
        }
    }

    private void addSectorIfNotNull(List<CircleChartView.Sector> sectors, String key, Float value, int color) {
        if (value != null && value > 0) {
            sectors.add(new CircleChartView.Sector(value, color));
        }
    }



    private HashMap<String, Float> incomeCircle() {
        HashMap<String, Float> percentageOfTheColorInCircle = new HashMap<>();
        float salary = 0, gifts = 0, bank = 0, payments = 0, stocks = 0, securities = 0, sell = 0, other = 0;
        float sum = 0;

        // Подсчет суммы для каждой категории
        for (Income income : _incomeList) {
            if (income != null && income.getAmount() > 0 && income.getCategoryId() != null) {
                sum += income.getAmount();
                switch (income.getCategoryId()) {
                    case "Зарплата":
                        salary += income.getAmount();
                        break;
                    case "Подарки":
                        gifts += income.getAmount();
                        break;
                    case "Проценты банка":
                        bank += income.getAmount();
                        break;
                    case "Гос. выплаты":
                        payments += income.getAmount();
                        break;
                    case "Акции":
                        stocks += income.getAmount();
                        break;
                    case "Ценные бумаги":
                        securities += income.getAmount();
                        break;
                    case "Продажа":
                        sell += income.getAmount();
                        break;
                    case "Другое":
                        other += income.getAmount();
                        break;
                    default:
                }
            }
        }

        // Проверка на деление на ноль
        if (sum > 0) {
            percentageOfTheColorInCircle.put("Зарплата", (salary * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Подарки", (gifts * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Проценты банка", (bank * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Гос. выплаты", (payments * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Акции", (stocks * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Ценные бумаги", (securities * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Продажа", (sell * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Другое", (other * 100.0f) / sum);
        } else {
            // Если сумма 0, присваиваем 0% всем категориям
            percentageOfTheColorInCircle.put("Зарплата", 0f);
            percentageOfTheColorInCircle.put("Подарки", 0f);
            percentageOfTheColorInCircle.put("Проценты банка", 0f);
            percentageOfTheColorInCircle.put("Гос. выплаты", 0f);
            percentageOfTheColorInCircle.put("Акции", 0f);
            percentageOfTheColorInCircle.put("Ценные бумаги", 0f);
            percentageOfTheColorInCircle.put("Продажа", 0f);
            percentageOfTheColorInCircle.put("Другое", 0f);
        }

        // Добавление суммы
        percentageOfTheColorInCircle.put("Сумма", sum);
        return percentageOfTheColorInCircle;
    }
    private HashMap<String, Float> wasteCircle() {

        HashMap<String, Float> percentageOfTheColorInCircle = new HashMap<>();
        float education = 0, product = 0, clothes = 0, hospital = 0, sport = 0, transport = 0, leisure = 0, other = 0;
        float sum = 0;

        for (Waste waste : _wasteList) {
            if (waste != null && waste.getAmount() > 0 && waste.getCategoryId() != null) {
                sum += waste.getAmount();
                switch (waste.getCategoryId()) {
                    case "Образование":
                        education += waste.getAmount();
                        break;
                    case "Продукты":
                        product += waste.getAmount();
                        break;
                    case "Одежда":
                        clothes += waste.getAmount();
                        break;
                    case "Больница":
                        hospital += waste.getAmount();
                        break;
                    case "Спорт":
                        sport += waste.getAmount();
                        break;
                    case "Транспорт":
                        transport += waste.getAmount();
                        break;
                    case "Досуг":
                        leisure += waste.getAmount();
                        break;
                    case "Другое":
                        other += waste.getAmount();
                        break;
                }
            }
        }

        if (sum > 0) {
            percentageOfTheColorInCircle.put("Образование", (education * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Продукты", (product * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Одежда", (clothes * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Больница", (hospital * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Спорт", (sport * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Транспорт", (transport * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Досуг", (leisure * 100.0f) / sum);
            percentageOfTheColorInCircle.put("Другое", (other * 100.0f) / sum);
        } else {
            percentageOfTheColorInCircle.put("Образование", 0f);
            percentageOfTheColorInCircle.put("Продукты", 0f);
            percentageOfTheColorInCircle.put("Одежда", 0f);
            percentageOfTheColorInCircle.put("Больница", 0f);
            percentageOfTheColorInCircle.put("Спорт", 0f);
            percentageOfTheColorInCircle.put("Транспорт", 0f);
            percentageOfTheColorInCircle.put("Досуг", 0f);
            percentageOfTheColorInCircle.put("Другое", 0f);
        }
        percentageOfTheColorInCircle.put("Сумма",sum);
        return percentageOfTheColorInCircle;
    }
    private void loadWasteData() {
        _wasteDAO.getWasteForUser(_userSP.getUserId(), new DBWaste.DataCallback<List<Waste>>() {
            @Override
            public void onSuccess(List<Waste> wastes) {
                _wasteList.clear();
                _wasteList.addAll(wastes);
                _adapterWaste.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    private void loadIncomeData(){
        _dbIncome.getIncomeForUser(_userSP.getUserId(), new DBIncome.DataCallback<List<Income>>() {
            @Override
            public void onSuccess(List<Income> data) {
                _incomeList.clear();
                _incomeList.addAll(data);
                _adapterIncome.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void createRecycleView(){
        if(_wasteOrIncome.equals("Расходы")){
            _recyclerView.setAdapter(_adapterWaste);
            loadWasteData();
            DrawCircleWaste();
        }
        else if (_wasteOrIncome.equals("Доходы")) {
            _recyclerView.setAdapter(_adapterIncome);
            loadIncomeData();
            DrawCircleIncome();
        }
    }

    private void ClickInTableLayout() {
        _tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                String tabText = tab.getText().toString();
                _wasteOrIncome = tabText;
                createRecycleView();
               
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
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.MenuNavigatorFindFriends){
            Intent intent = new Intent(this, AddFriendActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.MenuNavigatorMyFriends){
            Intent intent = new Intent(this, MyFriendActivity.class);
            startActivity(intent);
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
