package com.todos.api.dao;

import com.todos.api.model.Attachment;

import java.util.List;

public interface IAttachmentDAO extends ICRUD{
    List<Attachment> getAllAttachment();
    List<Attachment> getTaskAttachment(long taskId);
}
