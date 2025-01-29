package com.example.finance.Activity;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.Model.Friend;
import com.example.finance.R;

import java.util.ArrayList;
import java.util.List;

public class addFriendActivity extends AppCompatActivity {

    private SearchView searchView;
    private List<Friend> friendList;
    private RecyclerView  recyclerView;

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

        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }

    private void init(){
        searchView = findViewById(R.id.addFriendSearch);
    }

    private void searchList(String text){
        ArrayList<Friend> searchList = new ArrayList<>();
        for(Friend friend : friendList){

        }
     //   adapterFireBase.searchDataList(searchList); // Обновление адаптера
    }
}