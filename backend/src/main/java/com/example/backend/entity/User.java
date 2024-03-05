package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName(value = "user")  //表名
public class User {

    private String username;
    private String password;
    @TableId(type = IdType.AUTO)
    private Integer id;

    // 示例，待修改
    private String role;

    private String phone;
    private String idnumber;
    private String email;


    public User(String role, String username, String phone, String idnumber, String email, String password) {
        this.role = role;
        this.username = username;
        this.phone = phone;
        this.idnumber = idnumber;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
