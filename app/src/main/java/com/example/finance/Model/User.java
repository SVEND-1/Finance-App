package com.example.finance.Model;


import java.util.List;

public class User {
    private String _id;
    private String _login;
    private String _password;
    private int _balance;
    private List<Friend> _listFriends;
    private List<Income> _listIncome;
    private List<Waste> _listWaste;
    public User() {
    }

    public User(String login, String password,  int balance, List<Friend> listFriends,List<Income> listIncome,List<Waste> listWaste) {
        this._login = login;
        this._password = password;
        this._balance = balance;
        this._listFriends = listFriends;
        this._listIncome = listIncome;
        this._listWaste = listWaste;
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

    public List<Income> get_listIncome() {
        return _listIncome;
    }

    public void set_listIncome(List<Income> _listIncome) {
        this._listIncome = _listIncome;
    }

    public List<Waste> get_listWaste() {
        return _listWaste;
    }

    public void set_listWaste(List<Waste> _listWaste) {
        this._listWaste = _listWaste;
    }
}
