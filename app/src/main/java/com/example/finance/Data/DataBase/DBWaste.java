package com.example.finance.Data.DataBase;

import android.content.Context;
import android.widget.Toast;

import com.example.finance.Data.DAO;
import com.example.finance.Model.Waste;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DBWaste implements DAO<Waste,String> {
    private DatabaseReference databaseReference;
    private Context context;
    public DBWaste() {
        databaseReference = FirebaseDatabase.getInstance().getReference("waste");
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
