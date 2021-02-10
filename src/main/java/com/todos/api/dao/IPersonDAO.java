package com.todos.api.dao;

import com.todos.api.model.Person;

public interface IPersonDAO extends IDatabase {
    String table = "person";
    Person read(String email);
}
