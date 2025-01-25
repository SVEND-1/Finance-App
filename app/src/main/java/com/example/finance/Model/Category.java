package com.example.finance.Model;

public class Category {
    private String _id;
    private String _name;

    public Category() {
    }

    public Category(String _name, String _uriImageIcon) {
        this._name = _name;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

}
