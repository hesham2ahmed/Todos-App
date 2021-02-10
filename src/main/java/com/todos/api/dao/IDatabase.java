package com.todos.api.dao;

import java.util.List;

public interface IDatabase {
    <T> boolean insert(T object);
    <T> T read(long id);
    <T> boolean update(long id, T object);
    <T> List<T> getAll();
    boolean delete(long id);
}
