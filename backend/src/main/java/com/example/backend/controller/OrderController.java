package com.example.backend.controller;

import com.example.backend.Result;
import com.example.backend.entity.Order;
import com.example.backend.entity.QueryDTO;
import com.example.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    //展示用户的所有订单
    @RequestMapping("/api/order_show_user")
    @CrossOrigin
    public Result showOrderUser(@RequestBody QueryDTO queryDTO, HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        if(id == null) {
            return new Result(400,"用户未登录","");
        }
        return new Result(200, "", orderService.selectOrderByID(queryDTO,id));
    }

    //展示商店里需要发货、退款的订单
    @RequestMapping("/api/order_deal_shop")
    @CrossOrigin
    public Result showOrderShop(@RequestBody QueryDTO queryDTO, HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        if(id == null) {
            return new Result(400,"用户未登录","");
        }
        return new Result(200, "", orderService.selectOrderByShop(queryDTO,id));
    }

    //展示商店里所有商品相关的订单
    @RequestMapping("/api/order_all_shop")
    @CrossOrigin
    public Result showAllOrdersShop(@RequestBody QueryDTO queryDTO, HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        if(id == null) {
            return new Result(400,"用户未登录","");
        }
        return new Result(200, "", orderService.selectAllOrdersByShop(queryDTO,id));
    }

    //立即购买和购物车购买时创建订单(待支付)
    @RequestMapping("/api/order_create")
    @CrossOrigin
    public Result createOrder(@RequestBody Order order, HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        if(id == null) {
            return new Result(400,"用户未登录","");
        }
        order.setOwnerid(id);
        return orderService.addOrder(order);
    }

    //支付单个订单
    @RequestMapping("/api/order_pay_one")
    @CrossOrigin
    public Result payOrder(@RequestBody Order order) {
        return orderService.payOrder(order);
    }

    //支付多个订单
    @RequestMapping("/api/order_pay_more")
    @CrossOrigin
    public Result payOrders(@RequestBody List<Order> orders, HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        return orderService.payOrders(orders,id);
    }

    //给出同时支付信息
    @RequestMapping("/api/order_show_discount")
    @CrossOrigin
    public Result showOrderDiscount(@RequestBody List<Order> orders) {
        return new Result(200, "", orderService.orderDiscountInfo(orders));
    }

    //用户确认订单收货
    @RequestMapping("/api/order_confirm")
    @CrossOrigin
    public Result confirmOrder(@RequestBody Order order) {
        return orderService.confirmOrder(order);
    }

    //用户撤销订单
    @RequestMapping("/api/order_cancel")
    @CrossOrigin
    public Result cancelOrder(@RequestBody Order order) {
        return orderService.cancelOrder(order);
    }

    //用户退货退款
    @RequestMapping("/api/order_refund")
    @CrossOrigin
    public Result refundOrder(@RequestBody Order order) {
        return orderService.refundOrder(order);
    }

    //用户删除订单
    @RequestMapping("/api/order_delete")
    @CrossOrigin
    public Result deleteOrder(@RequestBody Order order) {
        return orderService.deleteOrder(order);
    }

    //商户订单发货
    @RequestMapping("/api/order_deliver")
    @CrossOrigin
    public Result deliverOrder(@RequestBody Order order) {
        return orderService.deliverOrder(order);
    }

    //商户同意退款
    @RequestMapping("/api/order_refund_pass")
    @CrossOrigin
    public Result refundOrderPass(@RequestBody Order order) {
        return orderService.passRefund(order);
    }
}
