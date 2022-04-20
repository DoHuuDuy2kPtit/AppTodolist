package com.example.todolist.response;

public class LoginRes {
    private String accessToken;

    public LoginRes(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
