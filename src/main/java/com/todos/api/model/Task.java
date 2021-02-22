package com.todos.api.model;

import java.util.Date;
import java.util.List;

public class Task {
    private long id;
    private String note;
    private String name;
    private Date created_date;
    private Date due_date;
    private boolean done;
    private List<Attachment> attachments;
    private long person_id;

    public Task(long person_id,String note, String name, Date created_date, Date due_date, boolean done) {
        this.note = note;
        this.name = name;
        this.created_date = created_date;
        this.due_date = due_date;
        this.done = done;
        this.person_id = person_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attachment){
        this.attachments.add(attachment);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getPerson_id() {
        return person_id;
    }
}
