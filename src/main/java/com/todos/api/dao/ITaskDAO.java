package com.todos.api.dao;

import com.todos.api.model.Task;

import java.util.List;

public interface ITaskDAO extends CRUD {
    String table = "task";
    List<Task> getPersonTasks(long userId);
}
