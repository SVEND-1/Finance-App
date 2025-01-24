package com.example.finance.Data.DataBase;

import com.example.finance.Data.DAO;
import com.example.finance.Model.Waste;

public class DBWaste implements DAO<Waste,String> {
    @Override
    public boolean insert(Waste model) {
        return false;
    }

    @Override
    public Waste read(String string) {
        return null;
    }

    @Override
    public boolean update(Waste model) {
        return false;
    }

    @Override
    public boolean delete(Waste model) {
        return false;
    }
}
