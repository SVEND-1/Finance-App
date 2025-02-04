package com.example.finance.Data.DataBase;


import com.example.finance.Data.DAO;
import com.example.finance.Model.Waste;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBWaste implements DAO<Waste, String> {
    private DatabaseReference databaseReference;

    public DBWaste() {
        databaseReference = FirebaseDatabase.getInstance().getReference("waste");
    }

    // Получение всех Waste для конкретного пользователя
    public void getWasteForUser(String userId, final DataCallback<List<Waste>> callback) {
        databaseReference.orderByChild("userId").equalTo(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Waste> wasteList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Waste waste = snapshot.getValue(Waste.class);
                            waste.setId(snapshot.getKey()); // Устанавливаем ID документа
                            wasteList.add(waste);
                        }
                        callback.onSuccess(wasteList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onError(databaseError.toException());
                    }
                });
    }

    // Получение Waste по ID
    public void getWasteById(String key, final DataCallback<List<Waste>> callback) {
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Waste waste = dataSnapshot.getValue(Waste.class);
                if (waste != null) {
                    waste.setId(dataSnapshot.getKey()); // Устанавливаем ID документа
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.toException());
            }
        });
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
    public Waste read(String string) {
        final Waste[] wastes = new Waste[1];
        databaseReference.child(string).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wastes[0] = dataSnapshot.getValue(Waste.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок
            }
        });
        return wastes[0];
    }

    public void readOneUser(String key, final DataCallback<Waste> callback) {
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Waste waste = dataSnapshot.getValue(Waste.class);
                if (waste != null) {
                    waste.setId(dataSnapshot.getKey()); // Устанавливаем ID документа
                }
                callback.onSuccess(waste);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.toException());
            }
        });
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

    public interface DataCallback<T> {
        void onSuccess(T data);
        void onError(Exception e);
    }
}