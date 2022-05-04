package com.example.todolist.response;

public class InfoRes {
    private int id;
    private String name,email,  address, phone_number, description;

    public InfoRes(String name, String email, String address, String phone_number, String description) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
