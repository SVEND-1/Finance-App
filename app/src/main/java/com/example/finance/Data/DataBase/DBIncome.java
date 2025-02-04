package com.example.finance.Data.DataBase;

import com.example.finance.Data.DAO;
import com.example.finance.Model.Income;
import com.example.finance.Model.Waste;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBIncome implements DAO<Income,String> {

    private DatabaseReference databaseReference;

    public DBIncome() {
        databaseReference = FirebaseDatabase.getInstance().getReference("income");
    }
    public interface DataCallback<T> {
        void onSuccess(T data);
        void onError(Exception e);
    }
    public void getIncomeForUser(String userId, final DBIncome.DataCallback<List<Income>> callback) {
        databaseReference.orderByChild("userId").equalTo(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Income> incomeList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Income income = snapshot.getValue(Income.class);
                            income.setId(snapshot.getKey()); // Устанавливаем ID документа
                            incomeList.add(income);
                        }
                        callback.onSuccess(incomeList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onError(databaseError.toException());
                    }
                });
    }

    @Override
    public boolean insert(Income model) {
        String key = databaseReference.push().getKey();
        if (key != null) {
            model.setId(key);
            databaseReference.child(key).setValue(model);
            return true;
        }
        return false;
    }

    @Override
    public Income read(String key) {
        final Income[] income = new Income[1];
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                income[0] = dataSnapshot.getValue(Income.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок
            }
        });
        return income[0];
    }

    @Override
    public boolean update(Income model) {
        if (model.getId() != null) {
            databaseReference.child(model.getId()).setValue(model);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Income model) {
        if (model.getId() != null) {
            databaseReference.child(model.getId()).removeValue();
            return true;
        }
        return false;
    }

}
