package com.example.backend.entity;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;

    //get set
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDTO() {

    }
//    @Override
//    public String toString() {
//        return
//    }
}
