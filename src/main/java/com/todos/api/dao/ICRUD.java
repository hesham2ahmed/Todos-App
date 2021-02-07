package com.todos.api.dao;

public interface ICRUD {
    <T> void insert(T object);
    <T> T read(long id);
    void update(long id, String...columns);
    void remove(long id);
}
