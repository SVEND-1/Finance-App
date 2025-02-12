package com.example.finance.Model;

import java.util.Date;
import java.util.Objects;

public class Waste {
    private String _id;
    private int _amount;
    private String _description;
    private String _userId;
    private String _categoryName;
    private Date _createdAt;

    public Waste() {
    }

    public Waste( int amount, String userId, String categoryName,String description,Date createdAt) {
        this._amount = amount;
        this._userId = userId;
        this._categoryName = categoryName;
        this._description = description;
        this._createdAt = createdAt;
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
        return _userId;
    }

    public void setUserId(String _userId) {
        this._userId = _userId;
    }

    public String getCategoryId() {
        return _categoryName;
    }

    public void setCategoryId(String _categoryName) {
        this._categoryName = _categoryName;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public Date get_createdAt() {
        return _createdAt;
    }

    public void set_createdAt(Date _createdAt) {
        this._createdAt = _createdAt;
    }

    @Override
    public String toString() {
        return "Waste{" +
                "_id='" + _id + '\'' +
                ", _amount=" + _amount +
                ", _description='" + _description + '\'' +
                ", _userId='" + _userId + '\'' +
                ", _categoryName='" + _categoryName + '\'' +
                ", _createdAt=" + _createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waste waste = (Waste) o;
        return _amount == waste._amount && Objects.equals(_id, waste._id) && Objects.equals(_description, waste._description) && Objects.equals(_userId, waste._userId) && Objects.equals(_categoryName, waste._categoryName) && Objects.equals(_createdAt, waste._createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, _amount, _description, _userId, _categoryName, _createdAt);
    }
}
