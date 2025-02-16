package com.example.finance.Model;

public class Friend {
    private String _id;
    private String _myUserId;
    private String _friendUserId;


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


    public String getFriendUserId() {
        return _friendUserId;
    }

}
