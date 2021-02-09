package com.todos.api.dao;

import com.todos.api.model.Attachment;

import java.util.List;

public interface IAttachmentDAO extends CRUD {
    String table = "attachment";
    List<Attachment> getTaskAttachments(long taskId);
}
