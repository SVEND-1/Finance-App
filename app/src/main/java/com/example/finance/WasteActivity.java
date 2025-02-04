package com.example.finance;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.Adapter.AdapterWaste;
import com.example.finance.Data.DataBase.DBWaste;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Model.Waste;

import java.util.ArrayList;
import java.util.List;

public class WasteActivity extends AppCompatActivity {

    private RecyclerView _recyclerView;
    private AdapterWaste _adapter;
    private List<Waste> _wasteList;
    private DBWaste _dbWaste;
    private SPUser _spUser;
    private String _userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste);

        _recyclerView = findViewById(R.id.recyclerView);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));

        _wasteList = new ArrayList<>();
        _adapter = new AdapterWaste(_wasteList);
        _recyclerView.setAdapter(_adapter);

        _dbWaste = new DBWaste();
        _spUser = new SPUser(this);
        _userId = getCurrentUserId(); // Получите ID текущего пользователя

        loadWasteData();
    }

    private void loadWasteData() {
        _dbWaste.getWasteForUser(_userId, new DBWaste.DataCallback<List<Waste>>() {
            @Override
            public void onSuccess(List<Waste> wastes) {
                _wasteList.clear();
                _wasteList.addAll(wastes);
                _adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                // Обработка ошибок
                Toast.makeText(WasteActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentUserId() {
        return _spUser.getUserId();
    }
}