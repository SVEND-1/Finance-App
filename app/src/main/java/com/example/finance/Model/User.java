package com.example.finance.Model;


import java.util.List;

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

//    public List<Friend> getListFriends() {
//        return _listFriends;
//    }
//
//    public void setListFriends(List<Friend> _listFriends) {
//        this._listFriends = _listFriends;
//    }
//
//    public List<Income> get_listIncome() {
//        return _listIncome;
//    }
//
//    public void set_listIncome(List<Income> _listIncome) {
//        this._listIncome = _listIncome;
//    }
//
//    public List<String> get_listWaste() {
//        return _listWasteId;
//    }
//
//    public void set_listWaste(List<String> _listWasteId) {
//        this._listWasteId = _listWasteId;
//    }
}
