package com.example.finance.Model;

import java.util.Date;
import java.util.Objects;

public class Income extends Transaction{
    public Income() {
    }

    public Income(int amount, String userId, String categoryName, String description, Date createdAt) {
        super(amount, userId, categoryName, description, createdAt);
    }
}
