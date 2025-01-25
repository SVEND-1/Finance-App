package com.example.finance.Model;

public class Friend {
    private String _id;
    private String _myUserId;
    private String _friendUserId;

    public Friend() {
    }

    public Friend(String _myUserId, String _friendUserId) {
        this._myUserId = _myUserId;
        this._friendUserId = _friendUserId;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getMyUserId() {
        return _myUserId;
    }

    public void setMyUserId(String _myUserId) {
        this._myUserId = _myUserId;
    }

    public String getFriendUserId() {
        return _friendUserId;
    }

    public void setFriendUserId(String _friendUserId) {
        this._friendUserId = _friendUserId;
    }
}
