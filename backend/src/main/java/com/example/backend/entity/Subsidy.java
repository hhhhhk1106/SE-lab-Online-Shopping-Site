package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "subsidy")
public class Subsidy {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String end_time;
    private String start_time;
    //商品类别，英文逗号分隔
    private String cate_list;
    private float fund;
    private float full; //满
    private float discount; //减
    private float fund_limit;
    private Integer sales_limit;    //销售量
    private float saleroom_limit;   //销售额
    private Integer state;  //0:新建, 1:进行中, 2:已结束, 3:余额不够 满减

    public Subsidy(String end_time, String start_time, String cate_list, float fund, float full, float discount, float fund_limit, Integer sales_limit, float saleroom_limit) {
        this.end_time = end_time;
        this.start_time = start_time;
        this.cate_list = cate_list;
        this.fund = fund;
        this.full = full;
        this.discount = discount;
        this.fund_limit = fund_limit;
        this.sales_limit = sales_limit;
        this.saleroom_limit = saleroom_limit;
        this.state = 0;
    }
    public Subsidy () {}

    public Integer getId() {
        return id;
    }

    public Integer getState() {
        return state;
    }

    public float getDiscount() {
        return discount;
    }

    public float getFull() {
        return full;
    }

    public float getFund() {
        return fund;
    }

    public float getFund_limit() {
        return fund_limit;
    }

    public float getSaleroom_limit() {
        return saleroom_limit;
    }

    public Integer getSales_limit() {
        return sales_limit;
    }

    public String getCate_list() {
        return cate_list;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setCate_list(String cate_list) {
        this.cate_list = cate_list;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setFull(float full) {
        this.full = full;
    }

    public void setFund(float fund) {
        this.fund = fund;
    }

    public void setFund_limit(float fund_limit) {
        this.fund_limit = fund_limit;
    }

    public void setSaleroom_limit(float saleroom_limit) {
        this.saleroom_limit = saleroom_limit;
    }

    public void setSales_limit(Integer sales_limit) {
        this.sales_limit = sales_limit;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
