package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "shoppingcart")
public class ShoppingCart {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userid;
    private Integer categoryid;

    private Integer quantity;
    //1-有效,2-失效
    private Integer catestate;

    public ShoppingCart(Integer userid, Integer categoryid, Integer quantity){
        this.userid = userid;
        this.categoryid = categoryid;
        this.quantity = quantity;
        this.catestate = 1;
    }

//    public ShoppingCart(Integer user_id, Integer category_id, Integer cate_state, Integer quantity){
//        this.user_id = user_id;
//        this.category_id = category_id;
//        this.quantity = quantity;
//        this.cate_state = cate_state;
//    }

//    public ShoppingCart(Integer id, Integer user_id, Integer category_id, Integer cate_state, Integer quantity){
//        this.id = id;
//        this.user_id = user_id;
//        this.category_id = category_id;
//        this.cate_state = cate_state;
//        this.quantity = quantity;
//    }

    public ShoppingCart() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Integer getCatestate() {
        return catestate;
    }

    public void setCatestate(Integer catestate) {
        this.catestate = catestate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
