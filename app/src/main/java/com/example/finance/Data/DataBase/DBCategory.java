package com.example.finance.Data.DataBase;

import com.example.finance.Data.DAO;
import com.example.finance.Model.Category;

public class DBCategory implements DAO<Category,String> {
    @Override
    public boolean insert(Category model) {
        return false;
    }

    @Override
    public Category read(String string) {
        return null;
    }

    @Override
    public boolean update(Category model) {
        return false;
    }

    @Override
    public boolean delete(Category model) {
        return false;
    }
}
