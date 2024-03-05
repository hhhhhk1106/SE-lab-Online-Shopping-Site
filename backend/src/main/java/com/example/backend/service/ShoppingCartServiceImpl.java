package com.example.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.Result;
import com.example.backend.entity.Category;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.ShoppingCart;
import com.example.backend.mapper.ShopMapper;
import com.example.backend.mapper.ShoppingCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Override
    public IPage<ShoppingCart> selectShoppingCartPage(QueryDTO queryDTO, Integer ownerID) {
        Page<ShoppingCart> page = new Page<>(queryDTO.getPageNo(), queryDTO.getPageSize());
        return shoppingCartMapper.selectShoppingCartPage(page, ownerID);
    }

    @Override
    public ShoppingCart searchShoppingCartByID(String id_str) {
        Integer id = Integer.parseInt(id_str);
        ShoppingCart shoppingCart = shoppingCartMapper.selectById(id);
        return shoppingCart;
    }

    @Override
    public Result addShoppingCart(Category category, Integer userID, Integer quantity){
        QueryWrapper<ShoppingCart> wrapper = new QueryWrapper<>();
        wrapper.eq("userid",userID);
        wrapper.eq("categoryid",category.getId());
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(wrapper);
        if(shoppingCart != null) {
            quantity += shoppingCart.getQuantity();
            shoppingCart.setQuantity(quantity);
            shoppingCartMapper.updateById(shoppingCart);
        } else {
            shoppingCart = new ShoppingCart(userID,category.getId(),quantity);
            shoppingCartMapper.insert(shoppingCart);
        }
        return new Result(200, "已在购物车添加该商品", shoppingCart);
    }

    @Override
    public Result changeShoppingCart(ShoppingCart shoppingCart){
        shoppingCartMapper.updateById(shoppingCart);
        //shoppingCartMapper.insert(shoppingCart);
        return new Result(200, "购物车商品数量修改", shoppingCart);
    }
    @Override
    public Result deleteShoppingCart(ShoppingCart shoppingCart){
        shoppingCartMapper.deleteById(shoppingCart.getId());
        return new Result(200,"已从购物车删除该商品",shoppingCart);
    }

}
