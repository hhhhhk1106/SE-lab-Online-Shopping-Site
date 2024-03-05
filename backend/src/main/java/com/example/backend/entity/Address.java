package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "address")
public class Address {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer ownerid;
    private String name;
    private String phone;
    private String address;

    public Address(Integer ownerid, String name, String phone, String address) {
        this.ownerid = ownerid;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Integer getOwnerid() {
        return ownerid;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
