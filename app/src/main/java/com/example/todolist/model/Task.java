package com.example.todolist.model;

import androidx.annotation.Nullable;

public class Task {
    private int id;
    private String title;
    private String dueDate;
    private String description;
    private int jobId;
    private int status;

    public Task(String title, String dueDate) {
        this.title = title;
        this.dueDate = dueDate;
    }

    public Task(String title, String dueDate, int status) {
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Task(String title, String dueDate, int status,@Nullable String description) {
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
        this.description = description;
    }

    public Task(int id, String title, int status, int jobId, @Nullable String description, String dueDate) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.jobId = jobId;
        this.status = status;
    }

    public Task(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return title;
    }
}
