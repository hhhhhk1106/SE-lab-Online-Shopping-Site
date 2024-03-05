package com.example.backend.controller;
import com.example.backend.Result;
import com.example.backend.entity.Category;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.ShoppingCart;
import com.example.backend.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    //通过购物车id返回购物车信息
    @RequestMapping("/api/shoppingCart_get_id")
    @CrossOrigin
    public Result accountList(@RequestBody String id_str) {
        ShoppingCart shoppingCart = shoppingCartService.searchShoppingCartByID(id_str);
        if(shoppingCart == null) {
            return new Result(400,"该条购物车信息不存在","");
        }
        return new Result(200,"",shoppingCart);
    }

    //给用户展示自己的购物车
    @RequestMapping("/api/shoppingCart_list_user")
    @CrossOrigin
    public Result shoppingCartListUser(@RequestBody QueryDTO queryDTO, HttpServletRequest request){
        Integer userID = (Integer) request.getSession().getAttribute("id");
        return new Result(200,"",shoppingCartService.selectShoppingCartPage(queryDTO, userID));
    }

    //用户添加购物车商品
    @RequestMapping("/api/shoppingCart_register")
    @CrossOrigin
    public Result addShoppingCart(@RequestBody Category category,@RequestParam(value = "quantity")Integer quantity, HttpServletRequest request) {
        Integer userID = (Integer) request.getSession().getAttribute("id");
        return shoppingCartService.addShoppingCart(category,userID,quantity);
    }

    @RequestMapping("/api/shoppingCart_change")
    @CrossOrigin
    public Result addShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.changeShoppingCart(shoppingCart);
    }

    //用户删除购物车商品
    @RequestMapping("/api/shoppingCart_delete")
    @CrossOrigin
    public Result deleteShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.deleteShoppingCart(shoppingCart);
    }
}
