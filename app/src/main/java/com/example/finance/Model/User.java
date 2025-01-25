package com.example.finance.Model;


import java.util.List;

public class User {
    private String _id;
    private String _login;
    private String _password;
    private int _balance;
    private List<Friend> _listFriends;
    private List<Income> _listIncome;
    public User() {
    }

    public User(String _login, String _password,  int _balance, List<Friend> _listFriends,List<Income> _listIncome) {
        this._login = _login;
        this._password = _password;
        this._balance = _balance;
        this._listFriends = _listFriends;
        this._listIncome = _listIncome;
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

    public List<Friend> getListFriends() {
        return _listFriends;
    }

    public void setListFriends(List<Friend> _listFriends) {
        this._listFriends = _listFriends;
    }
}
