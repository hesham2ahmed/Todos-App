package com.todos.api.dao;

import com.todos.api.model.Task;

import java.util.List;

public interface ITaskDAO extends ICRUD{
    List<Task> getAllTasks();
    List<Task> getUserTasks(long userId);
}
