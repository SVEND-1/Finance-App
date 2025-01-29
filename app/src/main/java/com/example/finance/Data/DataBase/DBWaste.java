package com.example.finance.Data.DataBase;

import android.content.Context;


import com.example.finance.Data.DAO;
import com.example.finance.Model.Waste;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBWaste implements DAO<Waste,String> {
    private DatabaseReference databaseReference;
    private Context context;
    public DBWaste() {
        databaseReference = FirebaseDatabase.getInstance().getReference("waste");
    }

    public void getWasteForUser(String userId, final DataCallback<List<Waste>> callback) {
        databaseReference.orderByChild("userId").equalTo(userId) // Измените "userId" на фактическое имя поля, по которому вы хотите фильтровать
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Waste> wasteList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Waste waste = snapshot.getValue(Waste.class);
                            wasteList.add(waste);
                        }
                        callback.onSuccess(wasteList); // Возврат списка расходов
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onError(databaseError.toException()); // Обработка ошибки
                    }
                });
    }

    public void getWasteById(String key, final DataCallback<Waste> callback) {
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Waste waste = dataSnapshot.getValue(Waste.class);
                callback.onSuccess(waste); // Вызов обратного вызова при успешном получении данных
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.toException()); // Обработка ошибки
            }
        });
    }
    public interface DataCallback<T> {
        void onSuccess(T data);

        void onError(Exception e);
    }

        @Override
    public boolean insert(Waste model) {
        String key = databaseReference.push().getKey();
        if (key != null) {
            model.setId(key);
            databaseReference.child(key).setValue(model);
            return true;
        }
        return false;
    }
    @Override
    public Waste read(String key) {
        final Waste[] waste = new Waste[1];
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                waste[0] = dataSnapshot.getValue(Waste.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return waste[0];
    }

    @Override
    public boolean update(Waste model) {
        if (model.getId() != null) {
            databaseReference.child(model.getId()).setValue(model);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Waste model) {
        if (model.getId() != null) {
            databaseReference.child(model.getId()).removeValue();
            return true;
        }
        return false;
    }
}
