package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.Result;
import com.example.backend.entity.Order;
import com.example.backend.entity.QueryDTO;

import java.util.List;
import java.util.Map;

public interface OrderService {
    public IPage<Order> selectOrderPage(QueryDTO queryDTO);
    public IPage<Order> selectOrderByID(QueryDTO queryDTO, Integer ownerid);
    public IPage<Order> selectOrderByShop(QueryDTO queryDTO, Integer merchant);
    public IPage<Order> selectAllOrdersByShop(QueryDTO queryDTO, Integer merchant);
    public Result addOrder(Order order);
    public Result payOrder(Order order);
    public Result payOrders(List<Order> orders, Integer ownerid);
    public Result deliverOrder(Order order);
    public Result confirmOrder(Order order);
    public Result cancelOrder(Order order);
    public Result refundOrder(Order order);
    public Result passRefund(Order order);
    public Result deleteOrder(Order order);
    public List<Integer> selectSubsidyFromOrders(List<Order> orderList);//判断orderList是否有活动，有几个
    public List<Order> selectOrdersWithTheSubsidy(List<Order> orderList, Integer subsidy_id);//orderList任意，选出带特定活动
    public Integer timesOfDiscount(List<Order> orderList, float full);//orderList为同一活动的
    public Map<String, Object> orderDiscountInfo(List<Order> orderList);
}
