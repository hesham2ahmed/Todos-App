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
    private final String SELECT_ALL_ATTACHMENT = "SELECT * FROM attachment";
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
            while(rs.next()){
                long id = rs.getLong("id");
                String link = rs.getString("link");
                Attachment attachment = new Attachment(id,link);
                attachments.add(attachment);
            }
        } catch (SQLException throwables) {
            ExceptionHandle.printSQLException(throwables);
        }
        return attachments;
    }

    @Override
    public List<Attachment> getTaskAttachment(long taskId) {
        return null;
    }

}
