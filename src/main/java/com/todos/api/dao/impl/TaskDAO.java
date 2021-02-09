package com.todos.api.dao.impl;

import com.todos.api.dao.ITaskDAO;
import com.todos.api.model.Person;
import com.todos.api.model.Task;
import com.todos.api.utility.ExceptionHandle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements ITaskDAO {


    private final String INSERT_TASK_SQL = "INSERT INTO " + table + " (person_id, task_note, task_name, created_date, due_date, is_done) " + "values(?, ?, ?, ?, ?, ?);";
    private final String SELECT_TASK_BY_ID_SQL = "SELECT person_id, task_note, task_name, created_date, due_date FROM " + table + " WHERE task_id = ?;";
    private final String UPDATE_TASK_SQL = "UPDATE task set task_note = ?, task_name = ?, due_date = ? WHERE task_id = ?;";
    private final String DELETE_TASK_SQL = "DELETE FROM person WHERE id = ?;";
    private final String SELECT_ALL_TASKS_SQL = "SELECT * FROM person;";
    private final String SELECT_PERSON_TASKS = "SELECT * FROM task where person_id = ?";

    private static TaskDAO inst = null;
    private final Connection connection;
    private PreparedStatement preparedStatement;

    /**
     * Constructor
     * @param connection
     * Single-tone
     */
    private TaskDAO(Connection connection){this.connection = connection; }
    public static TaskDAO createIns(Connection connection){
        if(inst == null)
            inst = new TaskDAO(connection);
        return inst;
    }

    @Override
    public <T> boolean insert(T object) {
        Task task = (Task) object;
        boolean added = false;
        try {
            preparedStatement = connection.prepareStatement(INSERT_TASK_SQL);
            preparedStatement.setLong(1, task.getPerson_id());
            preparedStatement.setString(2, task.getNote());
            preparedStatement.setString(3, task.getName());
            preparedStatement.setDate(4, (Date) task.getCreated_date());
            preparedStatement.setDate(5, (Date) task.getDue_date());
            preparedStatement.setBoolean(6, task.isDone());
            added = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return  added;
    }

    @Override
    public Task read(long id) {
        Task task = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                task = new Task(
                        resultSet.getLong("person_id"),
                        resultSet.getString("task_note"),
                        resultSet.getString("task_name"),
                        resultSet.getDate("created_date"),
                        resultSet.getDate("due_date"));
                task.setDone(resultSet.getBoolean("is_done"));
                task.setId(resultSet.getLong("task_id"));
            }
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return task;
    }

    @Override
    public <T> boolean update(long id, T object) {
        boolean updated = false;
        Task task = (Task) object;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_TASK_SQL);
            preparedStatement.setString(1, task.getNote());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setDate(3, (Date) task.getDue_date());
            preparedStatement.setLong(4, id);
            updated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_TASKS_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            tasks = getResultList(rs);
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return tasks;
    }

    @Override
    public boolean delete(long id) {
        boolean deleted = false;
        try {
            preparedStatement = connection.prepareStatement(DELETE_TASK_SQL);
            preparedStatement.setLong(1, id);
            deleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
    }

    @Override
    public List<Task> getPersonTasks(long personId) {
        List<Task> tasks = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_PERSON_TASKS);
            preparedStatement.setLong(1, personId);
            ResultSet rs = preparedStatement.executeQuery();
            tasks = getResultList(rs);
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return tasks;
    }

    private List<Task> getResultList(ResultSet rs) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        while(rs.next()){
            long task_id = rs.getLong("task_id");
            long person_id = rs.getLong("person_id");
            String task_note = rs.getString("task_note");
            String task_name = rs.getString("task_name");
            Date created_date = rs.getDate("created_date");
            Date due_date = rs.getDate("due_date");
            boolean is_done = rs.getBoolean("is_done");
            Task task = new Task(person_id, task_note, task_name, created_date, due_date);
            task.setId(task_id);
            task.setDone(is_done);
            tasks.add(task);
        }
        return tasks;
    }
}
