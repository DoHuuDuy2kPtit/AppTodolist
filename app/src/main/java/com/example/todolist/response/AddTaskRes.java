package com.example.todolist.response;

public class AddTaskRes {
    private String message;

    public AddTaskRes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
