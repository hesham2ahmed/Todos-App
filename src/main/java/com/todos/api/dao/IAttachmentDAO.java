package com.todos.api.dao;

import com.todos.api.model.Attachment;

import java.util.List;

public interface IAttachmentDAO{
    String table = "attachment";
    List<Attachment> getAllAttachment();
    List<Attachment> getTaskAttachments(long taskId);
}
