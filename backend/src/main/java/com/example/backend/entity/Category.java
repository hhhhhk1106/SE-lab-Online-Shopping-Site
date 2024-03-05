package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer id;

    //TOkDO:多张图片
    // 图片--图床链接（暂定
    private String imagePath;
    private String name;
    private String intro;
    private float price;

    //商品状态  0:申请注册   1:已通过（上架）   2:被拒绝的修改   3:旧商品修改申请中  4:新商品提交     5:商品下架  6:申请活动中
    private Integer state;
    private Integer shopID;
    private Integer ownerid;
    private Integer sales;
    private Integer subsidy_id;

    //TO-DO:图片
    public Category(String name,String intro,String imagePath,float price,Integer shopID,Integer ownerid) {
        this.name = name;
        this.intro = intro;
        this.imagePath = imagePath;
        this.price = price;
        this.state = 0;
        this.shopID = shopID;
        this.ownerid = ownerid;
        this.sales = 0;
        this.subsidy_id = 0;
    }
    public Category() {

    }

    public Integer getId() {
        return id;
    }

    public Integer getOwnerid() {
        return ownerid;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Integer getState() {
        return state;
    }

    public float getPrice() {
        return price;
    }

    public String getIntro() {
        return intro;
    }

    public Integer getShopID() {
        return shopID;
    }

    public String getName() {
        return name;
    }

    public Integer getSales() {
        return sales;
    }

    public Integer getSubsidy_id() {
        return subsidy_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }

    public void setShopID(Integer shopID) {
        this.shopID = shopID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public void setSubsidy_id(Integer subsidy_id) {
        this.subsidy_id = subsidy_id;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
