package com.example.todolist.response;

public class RegisterRes {
    private String message;

    public RegisterRes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
