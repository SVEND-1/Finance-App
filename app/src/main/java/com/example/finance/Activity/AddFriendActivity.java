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
import com.example.finance.Model.Friend;
import com.example.finance.Model.User;
import com.example.finance.R;

import java.util.ArrayList;
import java.util.List;

public class AddFriendActivity extends AppCompatActivity {

    private SearchView _searchView;
    private RecyclerView _recyclerView;
    private ArrayList<User> _userList;
    private DBUser _dbUser;
    private AdapterAddFriend _adapter;

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

        _searchView.clearFocus();

        _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void init() {
        _searchView = findViewById(R.id.addFriendSearch);
        _recyclerView = findViewById(R.id.addFriendrecyclerView);

        _dbUser = new DBUser();
        _dbUser.getAllUsers(new DBUser.UsersCallback() {
            @Override
            public void onCallback(ArrayList<User> users) {
                _userList = new ArrayList<>(users);
                _adapter = new AdapterAddFriend(AddFriendActivity.this, _userList);
                _recyclerView.setAdapter(_adapter);
            }
        });
    }

    private void searchList(String text) {
        ArrayList<User> searchList = new ArrayList<>();
        for (User user : _userList) {
            if (user.getLogin().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(user);
            }
        }
        _adapter.searchDataList(searchList);
    }
}