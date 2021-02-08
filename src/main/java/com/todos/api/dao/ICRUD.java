package com.todos.api.dao;

public interface ICRUD {
    <T> boolean insert(T object);
    <T> T read(long id);
    <T> boolean update(long id, T object);
    boolean delete(long id);
}
