package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "cateRecord")
public class CateRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    //待更新商品id
    private Integer old_category_id;
    //新上架申请/修改/活动申请商品id
    private Integer new_category_id;

    //申请人id（店主id）
    private Integer ownerid;

    //申请上架:register, 申请修改:update, 申请活动:subsidy
    private String operation;

    //待审核:0；通过:1；不通过:2  原商品失效:3
    private Integer state;

    public CateRecord(Integer ownerid, String operation, Integer new_category_id, Integer old_category_id) {
        this.ownerid = ownerid;
        this.operation = operation;
        this.new_category_id = new_category_id;
        this.old_category_id = old_category_id;
        this.state = 0;
    }
    public CateRecord(Integer ownerid,String operation,Integer new_category_id) {
        this.ownerid = ownerid;
        this.operation = operation;
        this.new_category_id = new_category_id;
        this.old_category_id = 0;
        this.state = 0;
    }

    public CateRecord() {

    }

    public Integer getId() {
        return id;
    }

    public Integer getOld_category_id() {
        return old_category_id;
    }

    public Integer getState() {
        return state;
    }

    public Integer getNew_category_id() {
        return new_category_id;
    }

    public String getOperation() {
        return operation;
    }

    public Integer getOwnerid() {
        return ownerid;
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

    public void setNew_category_id(Integer new_category_id) {
        this.new_category_id = new_category_id;
    }

    public void setOld_category_id(Integer old_category_id) {
        this.old_category_id = old_category_id;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
