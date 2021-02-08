package com.todos.api.dao;

import com.todos.api.model.Attachment;

import java.util.List;

public interface IAttachmentDAO{
    List<Attachment> getAllAttachment();
    List<Attachment> getTaskAttachment(long taskId);
}
