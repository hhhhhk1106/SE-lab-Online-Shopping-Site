package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "`order`")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer ownerid;
    private Integer addr_id;
    private Integer category_id;
    private Integer quantity;
    private String buy_time;
    private float payment;    //实付款
    private float allowance;    //补贴
    private Integer state = 0;  //0:待支付，1:待发货，2:待收货，3:已完成，4:已撤销，5:已退款, 6:退款中

    public Order(Integer ownerid, Integer addr_id, Integer category_id, Integer quantity, String buy_time) {
        this.ownerid = ownerid;
        this.addr_id = addr_id;
        this.category_id = category_id;
        this.quantity = quantity;
        this.buy_time = buy_time;
        this.payment = 0;
        this.allowance = 0;
        this.state = 0; //待支付
    }

    public Order() {}

    public Integer getOwnerid() {
        return ownerid;
    }

    public Integer getId() {
        return id;
    }

    public Integer getState() {
        return state;
    }

    public Integer getAddr_id() {
        return addr_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public float getAllowance() {
        return allowance;
    }

    public float getPayment() {
        return payment;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setAddr_id(Integer addr_id) {
        this.addr_id = addr_id;
    }

    public void setAllowance(float allowance) {
        this.allowance = allowance;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }
}
