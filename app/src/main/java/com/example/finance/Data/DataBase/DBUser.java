package com.example.finance.Data.DataBase;

import android.widget.Toast;

import com.example.finance.Activity.Login.RegisterActivity;
import com.example.finance.Data.DAO;
import com.example.finance.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DBUser implements DAO<User,String> {

    private DatabaseReference databaseReference;

    public DBUser() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }
    @Override
    public boolean insert(User model) {
        String key = databaseReference.push().getKey();
        if (key != null) {
            model.setId(key);
            databaseReference.child(key).setValue(model);
            return true;
        }
        return false;
    }

    @Override
    public User read(String key) {
        final User[] user = new User[1];
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user[0] = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок
            }
        });
        return user[0];
    }

    @Override
    public boolean update(User model) {
        if (model.getId() != null) {
            databaseReference.child(model.getId()).setValue(model);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(User model) {
        if (model.getId() != null) {
            databaseReference.child(model.getId()).removeValue();
            return true;
        }
        return false;
    }
}
