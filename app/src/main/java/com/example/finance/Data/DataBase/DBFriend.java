package com.example.finance.Data.DataBase;

import android.content.Context;

import com.example.finance.Data.DAO;
import com.example.finance.Data.SharedPreferences.SPUser;
import com.example.finance.Model.Friend;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBFriend implements DAO<Friend, String> {
    private DatabaseReference databaseReference;
    private SPUser _userSP;

    public DBFriend(Context context) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friends");
        _userSP = new SPUser(context);
    }

    public interface FriendsCallback {
        void onCallback(ArrayList<Friend> friends);
    }

    public void getAllFriends(DBFriend.FriendsCallback callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Friend> friends = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Friend friend = userSnapshot.getValue(Friend.class);
                    if (friend != null && friend.getMyUserId().equals(_userSP.getUserId())) {
                        friends.add(friend);
                    }
                }
                callback.onCallback(friends);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCallback(new ArrayList<>());
            }
        });
    }

    @Override
    public boolean insert(Friend model) {
        String key = databaseReference.push().getKey();
        if (key != null) {
            model.setId(key);
            databaseReference.child(key).setValue(model);
            return true;
        }
        return false;
    }

    @Override
    public Friend read(String key) {
        final Friend[] friend = new Friend[1];
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friend[0] = dataSnapshot.getValue(Friend.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок
            }
        });
        return friend[0];
    }

    @Override
    public boolean update(Friend model) {
        if (model.getId() != null) {
            databaseReference.child(model.getId()).setValue(model);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Friend model) {
        if (model.getId() != null) {
            databaseReference.child(model.getId()).removeValue();
            return true;
        }
        return false;
    }
}
