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
    private final String SELECT_ALL_ATTACHMENT = "SELECT * FROM " + table;
    private final String SELECT_TASK_ATTACHMENTS = "SELECT * FROM attachment INNER JOIN task_attach ta on ta.task_id = ?" ;
    private final Connection connection;
    private AttachmentDAO attachmentDAO = null;

    private AttachmentDAO(Connection connection) {
        this.connection = connection;
    }

    public AttachmentDAO createIns(Connection connection){
        if(attachmentDAO == null);
            attachmentDAO = new AttachmentDAO(connection);
        return attachmentDAO;
    }

    @Override
    public List<Attachment> getAllAttachment() {
        List<Attachment> attachments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ATTACHMENT);
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
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_ATTACHMENTS);
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
