package com.todos.api.dao;

import com.todos.api.model.Task;

import java.util.List;

public interface ITaskDAO extends ICRUD{
    String table = "task";
    List<Task> getAllTasks();

    List<Task> getPersonTasks(long userId);
}
