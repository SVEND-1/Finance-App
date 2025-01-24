package com.example.finance.Model;


public class User {
    private String _id;
    private String _login;
    private String _password;
    private String _uriImageIcon;
    private int _balance;
    public User() {
    }

    public User(String _id, String _login, String _password, String _uriImageIcon, int _balance) {
        this._id = _id;
        this._login = _login;
        this._password = _password;
        this._uriImageIcon = _uriImageIcon;
        this._balance = _balance;
    }

    public int get_balance() {
        return _balance;
    }

    public void set_balance(int _balance) {
        this._balance = _balance;
    }

    public String get_uriImageIcon() {
        return _uriImageIcon;
    }

    public void set_uriImageIcon(String _uriImageIcon) {
        this._uriImageIcon = _uriImageIcon;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_login() {
        return _login;
    }

    public void set_login(String _login) {
        this._login = _login;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
