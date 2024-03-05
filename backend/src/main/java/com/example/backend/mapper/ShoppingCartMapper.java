package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
    IPage<ShoppingCart> selectShoppingCartPage(Page<ShoppingCart> page, Integer ownerid);

    List<ShoppingCart> selectShoppingCartByCate(Integer categoryID);


//    IPage<ShoppingCart> selectShoppingCartNewPage(Page<ShoppingCart> page);
//    IPage<ShoppingCart> selectShoppingCartDeletePage(Page<ShoppingCart> page);
}