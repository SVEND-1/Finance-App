package com.example.finance.Data.DataBase;

import com.example.finance.Data.DAO;
import com.example.finance.Model.Friend;

public class DBFriend implements DAO<Friend,String> {
    @Override
    public boolean insert(Friend model) {
        return false;
    }

    @Override
    public Friend read(String string) {
        return null;
    }

    @Override
    public boolean update(Friend model) {
        return false;
    }

    @Override
    public boolean delete(Friend model) {
        return false;
    }
}
