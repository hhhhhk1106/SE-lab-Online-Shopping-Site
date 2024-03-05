package com.example.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.Result;
import com.example.backend.entity.*;
import com.example.backend.mapper.AccountMapper;
import com.example.backend.mapper.CategoryMapper;
import com.example.backend.mapper.OrderMapper;
import com.example.backend.mapper.SubsidyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SubsidyMapper subsidyMapper;
    @Autowired
    private SubsidyService subsidyService;

    @Override
    public IPage<Order> selectOrderPage(QueryDTO queryDTO) {
        Page<Order> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return orderMapper.selectOrderPage(page);
    }

    @Override
    public IPage<Order> selectOrderByID(QueryDTO queryDTO, Integer ownerid) {
        Page<Order> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return orderMapper.selectOrderByID(page, ownerid);
    }

    @Override
    public IPage<Order> selectOrderByShop(QueryDTO queryDTO, Integer merchant) {
        Page<Order> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return orderMapper.selectOrderByShop(page, merchant);
    }

    @Override
    public IPage<Order> selectAllOrdersByShop(QueryDTO queryDTO, Integer merchant) {
        Page<Order> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return orderMapper.selectAllOrderByShop(page, merchant);
    }

    @Override
    public Result addOrder(Order order) {
        //TOxDO: 两种controller，注意id，state
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        order.setBuy_time(time);
        orderMapper.insert(order);
        return new Result(200,"订单添加成功", order);
    }

    @Override
    public Result payOrder(Order order) {
        //TODO:test
        String errMessage = "";
        Boolean error = false;
        if(order.getState() != 0) {//待支付
            errMessage += "无需支付该订单\n";
            error = true;
        }

        Category category = categoryMapper.selectById(order.getCategory_id());
        //订单状态和商品状态都无误
        float total = category.getPrice() * order.getQuantity();
        float discount = 0; //活动折扣
        Integer sub_id = category.getSubsidy_id();
        if(!error) {
            //TODO:?判断商品状态？；判断活动（感觉之后支付单个可以不要?
            //活动时支付后checkSubsidyFund

            //活动判断
            if(sub_id > 0) {
                Subsidy subsidy = subsidyMapper.selectById(sub_id);
                List<Order> list = null;
                list.add(order);
                discount = timesOfDiscount(list, subsidy.getFull());
            }
            total -= discount;
            order.setPayment(total);
            order.setAllowance(discount);
            Result result = enoughMoneyOwner2Inter(total, order.getOwnerid());
            if(result.getCode()!=200) {
                //余额不足
                errMessage += result.getMessage();
                error = true;
            }
        }
        if(error) {
            return new Result(400,errMessage,"");
        }
        order.setState(1);
        orderMapper.updateById(order);
        if(discount > 0) {
            Subsidy subsidy = subsidyMapper.selectById(sub_id);
            subsidy.setFund(subsidy.getFund() - discount);
            subsidyMapper.updateById(subsidy);
            subsidyService.checkSubsidyFund(subsidy);
        }
        return new Result(200, "订单支付成功", order);
    }

    @Override
    public Result payOrders(List<Order> orders, Integer ownerid) {
        float total = 0;    //原价总和
        float discounts = 0;    //总折扣(可能有多个活动 多笔满减)
        for(Order order:orders) {
            Category category = categoryMapper.selectById(order.getCategory_id());
            float one_sum = order.getQuantity() * category.getPrice();
            order.setPayment(one_sum);
            total += one_sum;
        }

        //计算折扣
        List<Integer> subsidy_ids = selectSubsidyFromOrders(orders);
        List<Float> subsidy_discounts = new LinkedList<>();
        if(subsidy_ids != null) { //有活动
            for(Integer sub_id:subsidy_ids) {   //对涉及的每个活动进行判断
                List<Order> sub_orders = selectOrdersWithTheSubsidy(orders, sub_id);
                Subsidy subsidy = subsidyMapper.selectById(sub_id);
                //该活动所得的补贴(可能多次)
                float sub_discount = timesOfDiscount(sub_orders,subsidy.getFull()) * subsidy.getDiscount();
                discounts += sub_discount;
                subsidy_discounts.add(sub_discount);
            }
        }

        total -= discounts; //实付款
        //判断用户余额，足够则转账
        Result result = enoughMoneyOwner2Inter(total, ownerid);
        if(result.getCode()!=200) {
            //余额不足
            return new Result(400,result.getMessage(),"");
        }

        //TOkDO:对于活动的每个订单，处理allowance
        //活动资金扣款，判断余额
        if(discounts > 0) { //有活动补贴情况发生
//            for(Integer i = 0; i < subsidy_ids.size(); i ++) {
//                Subsidy subsidy = subsidyMapper.selectById(subsidy_ids.get(i));
//                float discount = subsidy_discounts.get(i);
//                if(discount == 0) { //虽然有活动但不满足补贴
//                    continue;
//                }
//                //对每个活动，从资金中扣除此次支付中该活动的补贴
//                subsidy.setFund(subsidy.getFund() - discount);
//                subsidyMapper.updateById(subsidy);
//                subsidyService.checkSubsidyFund(subsidy);
//
//                //按比例对参与该活动的订单分配补贴
//                List<Order> sub_orders = selectOrdersWithTheSubsidy(orders, subsidy_ids.get(i));
//                //参与该活动订单总原价
//                float sub_total_pay = getSum(sub_orders);
//                for(Order order:sub_orders) {
//                    Category category = categoryMapper.selectById(order.getCategory_id());
//                    float sub_one_pay = category.getPrice() * order.getQuantity();
//                    float allowance = sub_one_pay / sub_total_pay * discount;   //按比例分配
//                    order.setAllowance(allowance);
//                    order.setPayment(sub_one_pay - allowance);
//                    orderMapper.updateById(order);
//                }
//            }
            dealOrders(orders, subsidy_ids, subsidy_discounts);
        }

        for(Order order:orders) {
            order.setState(1);  //待发货
            orderMapper.updateById(order);
        }
        return new Result(200, "订单支付成功", orders);
    }

    public void dealOrders(List<Order> orders, List<Integer> subsidy_ids, List<Float> subsidy_discounts) {
        for(Integer i = 0; i < subsidy_ids.size(); i ++) {
            Subsidy subsidy = subsidyMapper.selectById(subsidy_ids.get(i));
            float discount = subsidy_discounts.get(i);
            if(discount == 0) { //虽然有活动但不满足补贴
                continue;
            }
            //对每个活动，从资金中扣除此次支付中该活动的补贴
            subsidy.setFund(subsidy.getFund() - discount);
            subsidyMapper.updateById(subsidy);
            subsidyService.checkSubsidyFund(subsidy);

            //按比例对参与该活动的订单分配补贴
            List<Order> sub_orders = selectOrdersWithTheSubsidy(orders, subsidy_ids.get(i));
            //参与该活动订单总原价
            float sub_total_pay = getSum(sub_orders);
            for(Order order:sub_orders) {
                Category category = categoryMapper.selectById(order.getCategory_id());
                float sub_one_pay = category.getPrice() * order.getQuantity();
                float allowance = sub_one_pay / sub_total_pay * discount;   //按比例分配
                order.setAllowance(allowance);
                order.setPayment(sub_one_pay - allowance);
                orderMapper.updateById(order);
            }
        }
    }

    @Override
    public Result deliverOrder(Order order) {
        String errMessage = "";
        Boolean error = false;
        if(order.getState() != 1) {//待发货
            errMessage += "无法对该订单进行发货\n";
            error = true;
        }
        if(error) {
            return new Result(400,errMessage,"");
        }
        order.setState(2);
        orderMapper.updateById(order);
        return new Result(200, "订单发货成功", order);
    }

    @Override
    public Result confirmOrder(Order order) {
        String errMessage = "";
        Boolean error = false;
        //TODO:活动
        if(order.getState() != 2) {//待收货
            errMessage += "不可确认该订单\n";
            error = true;
        }
        if(error) {
            return new Result(400,errMessage,"");
        }
        order.setState(3);
        orderMapper.updateById(order);

        Category category = categoryMapper.selectById(order.getCategory_id());
        //TODO: test,补贴处理

        //佣金
        float commission = (float) (order.getPayment() * 0.05);
        QueryWrapper<Account> wrapper_ac = new QueryWrapper<>();
        wrapper_ac.eq("type","profit");
        Account account_profit = accountMapper.selectOne(wrapper_ac);
        wrapper_ac.clear();
        wrapper_ac.eq("type","intermediate");
        Account account_inter = accountMapper.selectOne(wrapper_ac);
        wrapper_ac.clear();
        Integer merchant = category.getOwnerid();
        wrapper_ac.eq("ownerid", merchant);
        wrapper_ac.eq("type","shop");
        Account account_shop = accountMapper.selectOne(wrapper_ac);

        accountService.acTransfer(account_inter,account_profit,commission,"收取佣金");
        //商户收到去除佣金的付款和满减补贴
        accountService.acTransfer(account_inter,account_shop,order.getPayment() - commission + order.getAllowance(),"订单完成商户收款");
        //增加销量
        category.setSales(category.getSales() + order.getQuantity());
        categoryMapper.updateById(category);
        return new Result(200, "订单确认收货", order);
    }

    @Override
    public Result cancelOrder(Order order) {
        String errMessage = "";
        Boolean error = false;
        if(order.getState() != 0) {//待支付
            errMessage += "无法撤销该订单\n";
            error = true;
        }
        if(error) {
            return new Result(400,errMessage,"");
        }
        order.setState(4);//已撤销
        orderMapper.updateById(order);
        return new Result(200, "订单已撤销", order);
    }

    @Override
    public Result refundOrder(Order order) {
        //TOkDO: 活动商品不允许退款
        String errMessage = "";
        Boolean error = false;
        Category category = categoryMapper.selectById(order.getCategory_id());
        if(category.getSubsidy_id() != 0) {
            errMessage += "活动商品不允许退款\n";
            error = true;
        }
        if(order.getState() != 1 && order.getState() != 2) {//待发货 待收货
            errMessage += "无法退款\n";
            error = true;
        }
        if(error) {
            return new Result(400,errMessage,"");
        }
        order.setState(6);//退款中
        orderMapper.updateById(order);
        return new Result(200, "订单退款中", order);
    }

    @Override
    public Result passRefund(Order order) {
        QueryWrapper<Account> wrapper_ac = new QueryWrapper<>();
        wrapper_ac.eq("type","intermediate");
        Account account_inter = accountMapper.selectOne(wrapper_ac);
        wrapper_ac.clear();
        wrapper_ac.eq("ownerid", order.getOwnerid());
        wrapper_ac.eq("type","user");
        Account account_user = accountMapper.selectOne(wrapper_ac);
        accountService.acTransfer(account_inter,account_user,order.getPayment(),"订单退款");
        order.setState(5);//已退款
        orderMapper.updateById(order);
        return new Result(200, "订单已退款", order);
    }

    @Override
    public Result deleteOrder(Order order) {
        String errMessage = "";
        Boolean error = false;
        if(order.getState() < 3) {
            errMessage += "无法删除该订单\n";
            error = true;
        }
        if(error) {
            return new Result(400,errMessage,"");
        }
        orderMapper.deleteById(order.getId());
        return new Result(200, "订单已删除", order);
    }

    //判断orderList是否有活动，有几个
    @Override
    public List<Integer> selectSubsidyFromOrders(List<Order> orderList) {
        List<Integer> subsidy_ids = new LinkedList<>();
        Integer sub_id;
        for(Order order:orderList) {
            Category category = categoryMapper.selectById(order.getCategory_id());
            sub_id = category.getSubsidy_id();
            if(sub_id != null && sub_id != 0 && !subsidy_ids.contains(sub_id)) {
                subsidy_ids.add(sub_id);
            }
        }
        return subsidy_ids;
    }

    //orderList任意，选出带特定活动
    @Override
    public List<Order> selectOrdersWithTheSubsidy(List<Order> orderList, Integer subsidy_id) {
        List<Order> subsidyOrders = new LinkedList<>();
        for(Order order:orderList) {
            Category category = categoryMapper.selectById(order.getCategory_id());
            if(category.getSubsidy_id() == subsidy_id) {
                subsidyOrders.add(order);
            }
        }
        return subsidyOrders;
    }

    //orderList为同一活动的，计算满减次数
    @Override
    public Integer timesOfDiscount(List<Order> orderList, float full) {
        float pay = 0;
        for(Order order:orderList) {
            Category category = categoryMapper.selectById(order.getCategory_id());
            pay += category.getPrice() * order.getQuantity();
        }
        Integer times = (int) (pay / full);
        return times;
    }

    @Override
    public Map<String, Object> orderDiscountInfo(List<Order> orderList) {
        Map<String, Object> map = new HashMap<String, Object>();
        float total = 0;    //原价总和
        float payment = 0;
        float discounts = 0;    //总折扣(可能有多个活动 多笔满减)

        for(Order order:orderList) {
            Category category = categoryMapper.selectById(order.getCategory_id());
            float one_sum = order.getQuantity() * category.getPrice();
            total += one_sum;
        }
        payment = total;
        map.put("total", total);

        //计算折扣
        List<Integer> subsidy_ids = selectSubsidyFromOrders(orderList);
        map.put("subsidy_id_list", subsidy_ids);

        List<Float> subsidy_discounts = new LinkedList<>();
        List<List<Order>> subsidy_order_list = new LinkedList<>();
        if(subsidy_ids != null) { //有活动
            for(Integer sub_id:subsidy_ids) {   //对涉及的每个活动进行判断
                List<Order> sub_orders = selectOrdersWithTheSubsidy(orderList, sub_id);
                subsidy_order_list.add(sub_orders);
                Subsidy subsidy = subsidyMapper.selectById(sub_id);
                //该活动所得的补贴(可能多次)
                float sub_discount = timesOfDiscount(sub_orders,subsidy.getFull()) * subsidy.getDiscount();
                discounts += sub_discount;
                subsidy_discounts.add(sub_discount);
            }
        }
        map.put("subsidy_order_list", subsidy_order_list);
        map.put("subsidy_discount_list", subsidy_discounts);
        map.put("discount", discounts);

        payment -= discounts; //实付款
        map.put("payment", payment);

        return map;
    }

    public float getSum(List<Order> orderList) {
        float pay = 0;
        for(Order order:orderList) {
            Category category = categoryMapper.selectById(order.getCategory_id());
            pay += category.getPrice() * order.getQuantity();
        }
        return pay;
    }

    //判断付款账户是否有充足资金，有则扣款转给中间账户并写入流水，没有则返回相关错误信息
    public Result enoughMoneyOwner2Inter(float total, Integer ownerid) {
        QueryWrapper<Account> wrapper_ac = new QueryWrapper<>();
        wrapper_ac.eq("ownerid", ownerid);
        wrapper_ac.eq("type","user");
        Account account_owner = accountMapper.selectOne(wrapper_ac);
        wrapper_ac.clear();
        wrapper_ac.eq("type","intermediate");
        Account account_inter = accountMapper.selectOne(wrapper_ac);
        return accountService.acTransfer(account_owner, account_inter, total, "用户支付订单");
    }
}
