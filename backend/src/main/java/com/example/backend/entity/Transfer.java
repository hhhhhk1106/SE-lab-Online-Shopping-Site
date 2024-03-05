package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "transfer")
public class Transfer {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer account_in_id;
    private Integer account_out_id;
    private float money;

    private String message;
    private String timestamp;

    public Transfer(Integer account_in_id,Integer account_out_id,float money, String message, String timestamp) {
        this.account_in_id = account_in_id;
        this.account_out_id = account_out_id;
        this.money = money;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Transfer() {

    }

    public Integer getId() {
        return id;
    }

    public float getMoney() {
        return money;
    }

    public Integer getAccount_in_id() {
        return account_in_id;
    }

    public Integer getAccount_out_id() {
        return account_out_id;
    }

    public void setAccount_in_id(Integer account_in_id) {
        this.account_in_id = account_in_id;
    }

    public void setAccount_out_id(Integer account_out_id) {
        this.account_out_id = account_out_id;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
