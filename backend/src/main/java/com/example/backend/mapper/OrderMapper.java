package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    IPage<Order> selectOrderPage(Page<Order> page);
    IPage<Order> selectOrderByID(Page<Order> page, Integer ownerid);
    IPage<Order> selectOrderByShop(Page<Order> page, Integer merchant);
    IPage<Order> selectAllOrderByShop(Page<Order> page, Integer merchant);
}
