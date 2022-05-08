package com.example.todolist.response;

import com.example.todolist.model.Job;
import com.example.todolist.model.Task;

import java.util.ArrayList;

public class GetTaskRes {
    private ArrayList<Task> tasks;
    private String limit;
    private String offset;

    public GetTaskRes(ArrayList<Task> tasks, String limit, String offset) {
        this.tasks = tasks;
        this.limit = limit;
        this.offset = offset;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
