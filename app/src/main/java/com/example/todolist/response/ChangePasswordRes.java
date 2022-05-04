package com.example.todolist.response;

public class ChangePasswordRes {
    String message;

    public ChangePasswordRes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
