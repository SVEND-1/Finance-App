package com.example.finance.Model;


import java.util.List;
import java.util.Objects;

public class User {
    private String _id;
    private String _login;
    private String _password;
    private int _balance;

    public User() {
    }

    public User(String login, String password,  int balance) {
        this._login = login;
        this._password = password;
        this._balance = balance;
    }

    public int getBalance() {
        return _balance;
    }

    public void setBalance(int _balance) {
        this._balance = _balance;
    }


    public String getPassword() {
        return _password;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public String getLogin() {
        return _login;
    }

    public void setLogin(String _login) {
        this._login = _login;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return _balance == user._balance && Objects.equals(_id, user._id) && Objects.equals(_login, user._login) && Objects.equals(_password, user._password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, _login, _password, _balance);
    }
}
