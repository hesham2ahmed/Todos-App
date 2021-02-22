package com.todos.api.dao.impl;

import com.todos.api.dao.IAttachmentDAO;
import com.todos.api.model.Attachment;
import com.todos.api.utility.ExceptionHandle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDAO implements IAttachmentDAO {

    private final String INSERT_ATTACHMENT_SQL = "INSERT INTO " + table + "(link) values(?);";
    private final String READ_ATTACHMENT_SQL = "SELECT * FROM " + table + " WHERE id = ?;";
    private final String UPDATE_ATTACHMENT_SQL = "UPDATE " + table + " set link = ? WHERE id = ?;";
    private final String DELETE_ATTACHMENT_SQL = "DELETE FROM " + table + " WHERE id = ?;";
    private final String SELECT_ALL_ATTACHMENT_SQL = "SELECT * FROM " + table;
    private final String SELECT_TASK_ATTACHMENTS_SQL = "SELECT * FROM attachment INNER JOIN task_attach ta on ta.task_id = ?" ;

    private PreparedStatement preparedStatement;
    private final Connection connection;
    private AttachmentDAO attachmentDAO = null;

    /**
     * Single-tone Pattern
     * Constructor
     * @param connection
     */
    private AttachmentDAO(Connection connection) {
        this.connection = connection;
    }
    public AttachmentDAO createIns(Connection connection){
        if(attachmentDAO == null);
            attachmentDAO = new AttachmentDAO(connection);
        return attachmentDAO;
    }

    @Override
    public <T> long insert(T object) {
        long id = -1;
        Attachment attachment = (Attachment) object;
        try {
            preparedStatement = connection.prepareStatement(INSERT_ATTACHMENT_SQL);
            preparedStatement.setString(1, attachment.getLink());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                id = resultSet.getLong(1);
            }
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return id;
    }

    @Override
    public Attachment read(long id) {
        Attachment attachment = null;
        try {
            preparedStatement = connection.prepareStatement(READ_ATTACHMENT_SQL);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                attachment = new Attachment(id, rs.getString("link"));
            }
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return attachment;
    }

    @Override
    public <T> boolean update(long id, T object) {
        boolean updated = false;
        Attachment attachment = (Attachment) object;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ATTACHMENT_SQL);
            preparedStatement.setString(1, attachment.getLink());
            preparedStatement.setLong(2, id);
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
            preparedStatement = connection.prepareStatement(DELETE_ATTACHMENT_SQL);
            preparedStatement.setLong(1, id);
            deleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return deleted;
    }

    @Override
    public List<Attachment> getAll() {
        List<Attachment> attachments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ATTACHMENT_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            attachments = getResultList(rs);
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return attachments;
    }

    @Override
    public List<Attachment> getTaskAttachments(long taskId) {
        List<Attachment> attachments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_ATTACHMENTS_SQL);
            preparedStatement.setLong(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();
            attachments = getResultList(resultSet);
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return attachments;
    }

    private List<Attachment> getResultList(ResultSet resultSet) throws SQLException {
        List<Attachment> attachments = new ArrayList<>();
        while(resultSet.next()){
            long id = resultSet.getLong("id");
            String link = resultSet.getString("link");
            Attachment attachment = new Attachment(id,link);
            attachments.add(attachment);
        }
        return attachments;
    }
}
