package com.example.finance.Activity;

import android.os.Bundle;
import android.widget.SearchView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.Adapter.AdapterAddFriend;
import com.example.finance.Data.DataBase.DBUser;
import com.example.finance.Model.User;
import com.example.finance.R;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {

    private RecyclerView _recyclerView;
    private ArrayList<User> _originalUserList; // Список для хранения всех пользователей
    private DBUser _dbUser;
    private AdapterAddFriend _adapter;
    private SearchView _searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friend);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();


        //  _searchView.clearFocus();

//        _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchList(newText);
//                return true;
//            }
//        });

        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void init() {
        //_searchView = findViewById(R.id.addFriendSearch);
        _recyclerView = findViewById(R.id.addFriendrecyclerView);

        _dbUser = new DBUser();

        // Получаем всех пользователей из базы данных
        _dbUser.getAllUsers(new DBUser.UsersCallback() {
            @Override
            public void onCallback(ArrayList<User> users) {
                _originalUserList = new ArrayList<>(users);
                _adapter = new AdapterAddFriend(users);
                _recyclerView.setAdapter(_adapter); // Устанавливаем адаптер
            }
        });
    }

    private void searchList(String text) {
        ArrayList<User> searchList = new ArrayList<>();

        if (_originalUserList == null) {
            return;
        }

        if (text.isEmpty()) {
            searchList.addAll(_originalUserList);
        } else {
            for (User user : _originalUserList) {
                if (user.getLogin() != null && user.getLogin().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(user);
                }
            }
        }

        _adapter.searchDataList(searchList);
    }
}