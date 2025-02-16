package com.example.finance.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
import com.example.finance.Model.Waste;
import com.example.finance.MyView.CircleChartView;
import com.example.finance.R;
import com.example.finance.Utils.IncomeUtils;
import com.example.finance.Utils.WasteUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private WasteUtils _wasteUtils;
    private IncomeUtils _incomeUtils;

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
        _incomeUtils.DrawCircleIncome(_circleChartView);
        _wasteUtils.DrawCircleWaste(_circleChartView);

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

        _wasteUtils = new WasteUtils(_wasteList,_wasteDAO,this);
        _incomeUtils = new IncomeUtils(_incomeList,_dbIncome,this);
    }

    private void createRecycleView(){
        if(_wasteOrIncome.equals("Расходы")){
            _recyclerView.setAdapter(_adapterWaste);
            _wasteUtils.loadWasteData(_circleChartView,_adapterWaste,period);
            _wasteUtils.DrawCircleWaste(_circleChartView);
        }
        else if (_wasteOrIncome.equals("Доходы")) {
            _recyclerView.setAdapter(_adapterIncome);
            _incomeUtils.loadIncomeData(_circleChartView,_adapterIncome,period);
            _incomeUtils.DrawCircleIncome(_circleChartView);
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
                period = tabText;

                if(_wasteOrIncome.equals("Расходы")) {
                    _wasteList.clear();
                    _adapterWaste.notifyDataSetChanged();
                    _wasteUtils.loadWasteData(_circleChartView,_adapterWaste,period);
                }
                else if (_wasteOrIncome.equals("Доходы")) {
                    _incomeList.clear(); // Очищаем список доходов
                    _adapterIncome.notifyDataSetChanged(); // Уведомляем адаптер
                    _incomeUtils.loadIncomeData(_circleChartView,_adapterIncome, period);
                }
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
