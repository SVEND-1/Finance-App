package com.example.finance.Data.DataBase;

import com.example.finance.Data.DAO;
import com.example.finance.Model.User;

public class DBUser implements DAO<User,String> {
    @Override
    public boolean insert(User model) {
        return false;
    }

    @Override
    public User read(String string) {
        return null;
    }

    @Override
    public boolean update(User model) {
        return false;
    }

    @Override
    public boolean delete(User model) {
        return false;
    }
}
