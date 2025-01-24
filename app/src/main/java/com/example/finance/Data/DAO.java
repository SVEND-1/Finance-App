package com.example.finance.Data;

public interface DAO<Entity,Key> {
    boolean insert(Entity model);
    Entity read(Key key);
    boolean update(Entity model);
    boolean delete(Entity model);
}