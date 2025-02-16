package com.example.finance.Model;

import java.util.Date;
import java.util.Objects;

public class Transaction {
    protected String _id;
    protected int _amount;
    protected String _description;
    protected String _userId;
    protected String _categoryName;
    protected Date _createdAt;

    public Transaction() {
    }

    public Transaction(int amount, String userId, String categoryName, String description, Date createdAt) {
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

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }

    public Date getCreatedAt() {
        return _createdAt;
    }

    public void setCreatedAt(Date _createdAt) {
        this._createdAt = _createdAt;
    }

    @Override
    public String toString() {
        return "Transaction{" +
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
        Transaction that = (Transaction) o;
        return _amount == that._amount &&
                Objects.equals(_id, that._id) &&
                Objects.equals(_description, that._description) &&
                Objects.equals(_userId, that._userId) &&
                Objects.equals(_categoryName, that._categoryName) &&
                Objects.equals(_createdAt, that._createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, _amount, _description, _userId, _categoryName, _createdAt);
    }
}