package com.example.finance.Model;

import java.util.Date;
import java.util.Objects;

public class Waste extends Transaction {
    public Waste() {
    }

    public Waste(int amount, String userId, String categoryName, String description, Date createdAt) {
        super(amount, userId, categoryName, description, createdAt);
    }
}
