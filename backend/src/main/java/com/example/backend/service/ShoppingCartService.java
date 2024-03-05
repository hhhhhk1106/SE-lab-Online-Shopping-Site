package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.Result;
import com.example.backend.entity.Category;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.ShoppingCart;

public interface ShoppingCartService {
    public IPage<ShoppingCart> selectShoppingCartPage(QueryDTO queryDTO, Integer userID);
    public ShoppingCart searchShoppingCartByID(String id_str);
    public Result addShoppingCart(Category category, Integer userID, Integer quantity);
    public Result deleteShoppingCart(ShoppingCart shoppingCart);
    public Result changeShoppingCart(ShoppingCart shoppingCart);
}
