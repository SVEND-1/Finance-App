package com.example.finance.Data.DataBase;

import com.example.finance.Data.DAO;
import com.example.finance.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBUser implements DAO<User, String> {

    private DatabaseReference databaseReference;

    public DBUser() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    //Для поиска человека
    public interface UserCallback {
        void onCallback(User user);
    }

    public void isEmptyUser(String login, UserCallback callback) {
        databaseReference.orderByChild("login").equalTo(login).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = null; // Локальная переменная для хранения найденного пользователя
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        user = userSnapshot.getValue(User.class); // Получаем пользователя
                    }
                }
                // Вызываем обратный вызов с найденным пользователем
                callback.onCallback(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCallback(null); // Вызываем обратный вызов с null в случае ошибки
            }
        });
    }

    public void findUserFromId(String id, UserCallback callback) {
        databaseReference.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = null; // Локальная переменная для хранения найденного пользователя
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        user = userSnapshot.getValue(User.class); // Получаем пользователя
                    }
                }
                // Вызываем обратный вызов с найденным пользователем
                callback.onCallback(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCallback(null); // Вызываем обратный вызов с null в случае ошибки
            }
        });
    }

    public interface UsersCallback {
        void onCallback(ArrayList<User> users);
    }

    public void getAllUsers(UsersCallback callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> users = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        users.add(user);
                    }
                }
                callback.onCallback(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCallback(new ArrayList<>());
            }
        });
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
