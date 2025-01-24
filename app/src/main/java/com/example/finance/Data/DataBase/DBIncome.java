package com.example.finance.Data.DataBase;

import com.example.finance.Data.DAO;
import com.example.finance.Model.Income;

public class DBIncome implements DAO<Income,String> {
    @Override
    public boolean insert(Income model) {
        return false;
    }

    @Override
    public Income read(String string) {
        return null;
    }

    @Override
    public boolean update(Income model) {
        return false;
    }

    @Override
    public boolean delete(Income model) {
        return false;
    }
}
