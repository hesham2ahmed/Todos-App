package com.todos.api.dao;

import com.todos.api.model.Attachment;

import java.util.List;

public interface IAttachmentDAO extends IDatabase {
    String table = "attachment";
    List<Attachment> getTaskAttachments(long taskId);
}
