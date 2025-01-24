package com.example.finance.Model;

public class Friend {
    private String _id;
    private String _myUserId;
    private String _friendUserId;

    public Friend() {
    }

    public Friend(String _id, String _myUserId, String _friendUserId) {
        this._id = _id;
        this._myUserId = _myUserId;
        this._friendUserId = _friendUserId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_myUserId() {
        return _myUserId;
    }

    public void set_myUserId(String _myUserId) {
        this._myUserId = _myUserId;
    }

    public String get_friendUserId() {
        return _friendUserId;
    }

    public void set_friendUserId(String _friendUserId) {
        this._friendUserId = _friendUserId;
    }
}
