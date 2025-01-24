package com.example.finance.Model;

public class Category {
    private String _id;
    private String _name;
    private String _uriImageIcon;

    public Category() {
    }

    public Category(String _id, String _name, String _uriImageIcon) {
        this._id = _id;
        this._name = _name;
        this._uriImageIcon = _uriImageIcon;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_uriImageIcon() {
        return _uriImageIcon;
    }

    public void set_uriImageIcon(String _uriImageIcon) {
        this._uriImageIcon = _uriImageIcon;
    }
}
