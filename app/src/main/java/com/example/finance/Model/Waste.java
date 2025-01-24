package com.example.finance.Model;

public class Waste {
    private String _id;
    private int _amount;
    private String _user_id;
    private String _category_id;

    public Waste() {
    }

    public Waste(String _id, int _amount, String _user_id, String _category_id) {
        this._id = _id;
        this._amount = _amount;
        this._user_id = _user_id;
        this._category_id = _category_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int get_amount() {
        return _amount;
    }

    public void set_amount(int _amount) {
        this._amount = _amount;
    }

    public String get_user_id() {
        return _user_id;
    }

    public void set_user_id(String _user_id) {
        this._user_id = _user_id;
    }

    public String get_category_id() {
        return _category_id;
    }

    public void set_category_id(String _category_id) {
        this._category_id = _category_id;
    }
}
