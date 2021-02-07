package com.todos.api.dao;

import com.todos.api.model.Person;
import java.util.List;

public interface IPersonDAO extends ICRUD {
    List<Person> getAllPersons();
}
