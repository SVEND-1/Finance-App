package com.example.finance.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.Adapter.AdapterAddFriend;
import com.example.finance.Adapter.AdapterMyFriend;
import com.example.finance.Data.DataBase.DBFriend;
import com.example.finance.Data.DataBase.DBUser;
import com.example.finance.Model.Friend;
import com.example.finance.Model.User;
import com.example.finance.R;

import java.util.ArrayList;

public class MyFriendActivity extends AppCompatActivity {

    private RecyclerView _recyclerView;
    private DBFriend _dbFriend;
    private AdapterMyFriend _adapter;
    private ArrayList<Friend> friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_friend);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void init(){
        _recyclerView = findViewById(R.id.myFriendrecyclerView);
        _dbFriend = new DBFriend(this);


        _dbFriend.getAllFriends(new DBFriend.FriendsCallback() {
            @Override
            public void onCallback(ArrayList<Friend> friends) {
                MyFriendActivity.this.friends = new ArrayList<>(friends); // Сохраняем в поле класса
                _adapter = new AdapterMyFriend(MyFriendActivity.this.friends);
                _recyclerView.setAdapter(_adapter);
            }
        });
    }
}