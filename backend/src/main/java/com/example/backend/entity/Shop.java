package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "shop")
public class Shop {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer ownerid;

    private String storename;
    private String category;//什么数据类型
    private String idnumber;
    private String intro;//介绍
    private String address;
    private String fund;
    private String date;
    private Integer subsidy_id;

    // 开店请求状态   0:申请注册   1:已通过   2:申请删除且有资格    3:申请删除但无资格   4:申请活动中
    private Integer state;

    public Shop(String storename,String category,String idnumber,String intro,String address,String fund,String date){
        this.storename = storename;
        this.category = category;
        this.idnumber = idnumber;
        this.intro = intro;
        this.address = address;
        this.fund = fund;
        this.date = date;
        this.subsidy_id = 0;
        this.state = 0;
    }

    public Shop() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getCategory() {
        return category;
    }

    public Integer getSubsidy_id() {
        return subsidy_id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public void setSubsidy_id(Integer subsidy_id) {
        this.subsidy_id = subsidy_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }
    public float getFund_float() {
        float f = Float.parseFloat(fund);
        return f;
    }
}
