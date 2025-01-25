package com.example.finance.Model;

public class Waste {
    private String _id;
    private int _amount;
    private String _description;
    private String _user_id;
    private String _category_id;

    public Waste() {
    }

    public Waste( int _amount, String _user_id, String _category_id) {
        this._amount = _amount;
        this._user_id = _user_id;
        this._category_id = _category_id;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public int getAmount() {
        return _amount;
    }

    public void setAmount(int _amount) {
        this._amount = _amount;
    }

    public String getUserId() {
        return _user_id;
    }

    public void setUserId(String _user_id) {
        this._user_id = _user_id;
    }

    public String getCategoryId() {
        return _category_id;
    }

    public void setCategoryId(String _category_id) {
        this._category_id = _category_id;
    }
}
