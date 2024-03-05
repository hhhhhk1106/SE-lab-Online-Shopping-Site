package com.example.backend.entity;

import java.io.Serializable;

public class LoginVO implements Serializable {
    private Integer id;
    private String token;
    private User user;

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
