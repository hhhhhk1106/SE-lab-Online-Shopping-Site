package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "account")
public class Account {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer ownerid;//所属者的id

    // (for user/merchant)  user:个人账户, shop:商店账户
    // (for admin)  profit:利润账户, intermediate:中间账户
    private String type;

    private float balance;//余额

    public Account(Integer ownerid, String type, float balance) {
        this.ownerid = ownerid;
        this.type = type;
        this.balance = balance;
    }
    public Account() {

    }

    public Integer getId() {
        return id;
    }

    public float getBalance() {
        return balance;
    }

    public Integer getOwnerid() {
        return ownerid;
    }

    public String getType() {
        return type;
    }

    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setType(String type) {
        this.type = type;
    }

    //充值功能
    public void recharge(float money) {
        this.balance += money;
    }
}

