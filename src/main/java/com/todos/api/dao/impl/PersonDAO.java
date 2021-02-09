package com.todos.api.dao.impl;

import com.todos.api.dao.IPersonDAO;
import com.todos.api.model.Person;
import com.todos.api.model.Task;
import com.todos.api.utility.ExceptionHandle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements IPersonDAO {

    private final String INSERT_PERSON_SQL = "INSERT INTO " + table + " (first_name, last_name, email, pass) " + "values(?, ?, ?, ?);";
    private final String SELECT_PERSON_BY_ID_SQL = "SELECT email, first_name, last_name, pass FROM " + table + " WHERE id = ?;";
    private final String SELECT_PERSON_BY_EMAIL_SQL = "SELECT id, first_name, last_name, pass FROM " + table + " WHERE email = ?;";
    private final String UPDATE_PERSON_SQL = "UPDATE " + table + " set email = ?, first_name = ?, last_name = ?, pass = ? WHERE id = ?;";
    private final String DELETE_PERSON_SQL = "DELETE FROM "+ table +" WHERE id = ?;";
    private final String SELECT_ALL_PERSONS_SQL = "SELECT * FROM " + table + ";";

    private static PersonDAO inst = null;
    private final Connection connection;
    PreparedStatement preparedStatement;

    /**
     * Constructor
     * @param connection
     * Single-tone Pattern
     */
    private PersonDAO(Connection connection){this.connection = connection; }
    public static PersonDAO createIns(Connection connection){
        if(inst == null)
            inst = new PersonDAO(connection);
        return inst;
    }

    @Override
    public <T> boolean insert(T object) {
        Person person = (Person) object;
        boolean inserted = false;
        try {

            preparedStatement = connection.prepareStatement(INSERT_PERSON_SQL);
            preparedStatement.setString(1, person.getFirst_name());
            preparedStatement.setString(2, person.getLast_name());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setString(4, person.getPassword());
            inserted = preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return inserted;
    }

    @Override
    public Person read(long id) {
        Person person = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_PERSON_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet =  preparedStatement.executeQuery();
            while(resultSet.next())
            {
                person = new Person(
                    resultSet.getString("email"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("pass"));
            }
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return person;
    }

    public Person read(String email){
        Person person = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_PERSON_BY_EMAIL_SQL);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                person = new Person(
                        email,
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("pass"));
                person.setId(resultSet.getLong("id"));
            }
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return person;
    }

    @Override
    public <T> boolean update(long id, T object) {
        boolean updated = false;
        Person person = (Person) object;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PERSON_SQL);
            preparedStatement.setString(1, person.getEmail());
            preparedStatement.setString(2, person.getFirst_name());
            preparedStatement.setString(3, person.getLast_name());
            preparedStatement.setString(4, person.getPassword());
            updated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return updated;
    }

    @Override
    public boolean delete(long id) {
        boolean deleted = false;
        try {
            preparedStatement = connection.prepareStatement(DELETE_PERSON_SQL);
            preparedStatement.setLong(1, id);
            deleted = preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return deleted;
    }

    @Override
    public List<Person> getAll() {
        List<Person> people= null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PERSONS_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            people = getResultList(rs);
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return people;
    }

    private List<Person> getResultList(ResultSet rs) throws SQLException {
        List<Person> people = new ArrayList<>();
        while(rs.next()){
            long id = rs.getLong("id");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String email = rs.getString("email");
            String pass = rs.getString("pass");
            Person person = new Person(email, first_name, last_name, pass);
            person.setId(id);
            people.add(person);
        }
        return people;
    }



}
