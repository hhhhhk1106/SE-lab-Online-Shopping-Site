package com.example.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.Result;
import com.example.backend.entity.*;
import com.example.backend.mapper.AccountMapper;
import com.example.backend.mapper.CategoryMapper;
import com.example.backend.mapper.OrderMapper;
import com.example.backend.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ShopServiceImpl implements ShopService{
    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public IPage<Shop> selectShopPage(QueryDTO queryDTO) {
        Page<Shop> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return shopMapper.selectShopPage(page, queryDTO.getKeyword());
    }

    @Override
    public IPage<Shop> selectShopNewPage(QueryDTO queryDTO) {
        Page<Shop> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return shopMapper.selectShopNewPage(page, queryDTO.getKeyword());
    }

    @Override
    public IPage<Shop> selectShopApplyPage(QueryDTO queryDTO) {
        Page<Shop> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return shopMapper.selectShopApplyPage(page);
    }

    @Override
    public IPage<Shop> selectShopDeletePage(QueryDTO queryDTO) {
        Page<Shop> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return shopMapper.selectShopDeletePage(page);
    }

    @Override
    public IPage<Shop> selectShopPageOwner(QueryDTO queryDTO, Integer id) {
        Page<Shop> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return shopMapper.selectShopPageOwner(page, id);
    }

    @Override
    public Shop searchShopByID(String id_str) {
        Integer id = Integer.parseInt(id_str);
        Shop shop = shopMapper.selectById(id);
        return shop;
    }

    @Override
    public Result applyShopSubsidy(Subsidy subsidy, Integer ownerid) {
        String errMessage = "";
        boolean error = false;

        QueryWrapper<Shop> wrapper = new QueryWrapper<>();
        wrapper.eq("ownerid", ownerid);
        Shop shop = shopMapper.selectOne(wrapper);
        if(shop.getState() != 1) {  //正常开张的店铺
            errMessage += "此店铺状态不能申请活动\n";
            error = true;
        } else if(shop.getSubsidy_id() != 0 && shop.getState() != 4) {
            errMessage += "此店铺已有活动正在进行\n";
            error = true;
        } else {
            if(shop.getFund_float() < subsidy.getFund_limit()) {
                errMessage += "此店铺注册资金不足\n";
                error = true;
            }

            List<Category> list = categoryMapper.selectCateByShop(shop.getId());
            Integer sales = 0;
            float saleroom = 0;
            for (Category category:list) {
                sales += category.getSales();
                saleroom += category.getPrice() * category.getSales();
            }

            if(sales < subsidy.getSales_limit()) {
                errMessage += "此店铺销量不足\n";
                error = true;
            }
            if(saleroom < subsidy.getSaleroom_limit()) {
                errMessage += "此店铺销售额不足\n";
                error = true;
            }
        }

        if(error) {
            return new Result(400,errMessage,"");
        }
        shop.setState(4);
        shop.setSubsidy_id(subsidy.getId());
        shopMapper.updateById(shop);
        return new Result(200,"店铺申请活动成功",shop);
    }

    @Override
    public Result passShopSubsidy(Shop shop) {
        shop.setState(1);
        shopMapper.updateById(shop);
        return new Result(200, "商店申请活动通过", shop);
    }

    @Override
    public Result notPassShopSubsidy(Shop shop) {
        shop.setState(1);
        shop.setSubsidy_id(0);
        shopMapper.updateById(shop);
        return new Result(200, "商店申请活动不通过", shop);
    }

    @Override
    public Result addShop(Shop shop, Integer id) {
        shop.setOwnerid(id);
        shop.setState(0);
        QueryWrapper<Shop> wrapper = new QueryWrapper<>();
        QueryWrapper<Account> wrapper_ac = new QueryWrapper<>();
        //ShopNew shopNew1 = shopNewMapper.selectOne(wrapper);

        // 通过角色查询，判断是否商户//拦截器   //已实现
//        if(shopNew.getCategory() != "merchant") {
//            return new Result(400, "非商户不能开店", "");
//        }
        String errMessage = "";
        boolean error = false;

        // 通过身份证号查询，判断是否已有相同身份证号
        wrapper.eq("idnumber", shop.getIdnumber());
        Shop shopNew_idnumber = shopMapper.selectOne(wrapper);
        if(shopNew_idnumber!=null) {
            errMessage += "身份证号不能重复\n";
            error = true;
        }

        // 通过注册资金查询，判断是否为浮点数且大于1000
        Pattern patternInteger = Pattern.compile("^[-\\+]?[\\d]*$");
        Pattern patternFloat = Pattern.compile("^[-\\+]?([1-9]\\d*.\\d*|0\\.\\d*[1-9]\\d*)");

        if(patternInteger.matcher(shop.getFund()).matches()) {
            int i = Integer.parseInt(shop.getFund());
            if(i >= 1000){
                shop.setFund(Float.toString((float)i));
            }else{
                errMessage += "注册资金未达到1000\n";
                error = true;
            }
            shop.setFund(Float.toString((float)i));
            //System.out.println("int"+i);
        }else if(patternFloat.matcher(shop.getFund()).matches()){
            float f = Float.parseFloat(shop.getFund());
            //System.out.println(f);
            if(f < 1000.0) {
                errMessage += "注册资金未达到1000\n";
                error = true;
            }
        }else{
            errMessage += "资金输入的不是数字\n";
            error = true;
        }

//        Account account_shop = new Account(id, "shop", 0);
        wrapper_ac.eq("ownerid", id);
        wrapper_ac.eq("type","user");
        Account account_owner = accountMapper.selectOne(wrapper_ac);
//        wrapper_ac.clear();
//        wrapper_ac.eq("ownerid", id);
//        wrapper_ac.eq("type","shop");
//        Account account_oldShop = accountMapper.selectOne(wrapper_ac);
        wrapper.clear();
        wrapper.eq("ownerid", id);
        Shop oldShop = shopMapper.selectOne(wrapper);

        if(oldShop!=null) {
            errMessage += "商店至多注册一个\n";
            error = true;
        } else {
            //System.out.println(account_owner.getOwnerid());
            wrapper_ac.clear();
            wrapper_ac.eq("type","intermediate");
            Account account_inter = accountMapper.selectOne(wrapper_ac);
            //System.out.println(account_inter.getOwnerid());

            //float f = Float.parseFloat(shop.getFund());
            Result result = accountService.acTransfer(account_owner, account_inter, shop.getFund_float(), "商户注册商店");
            if(result.getCode()!=200) {
                //余额不足
                //errMessage += "id"+id.toString();
                errMessage += result.getMessage();
                error = true;
            }
        }

        if(error) {
            return new Result(400,errMessage,"");
        }

        shopMapper.insert(shop);
        //accountMapper.insert(account_shop);

        return new Result(200, "", shop);
    }
    @Override
    public Result passShop(Shop shop) {
        shop.setState(1);
        shopMapper.updateById(shop);
        Account account_shop = new Account(shop.getOwnerid(), "shop", 0);
        accountMapper.insert(account_shop);
        //转账
        QueryWrapper<Account> wrapper_ac = new QueryWrapper<>();
        wrapper_ac.eq("type","intermediate");
        Account account_inter = accountMapper.selectOne(wrapper_ac);
        wrapper_ac.clear();
        wrapper_ac.eq("type","profit");
        Account account_profit = accountMapper.selectOne(wrapper_ac);
        accountService.acTransfer(account_inter,account_profit,shop.getFund_float(),"商店注册通过");

        return new Result(200, "商店注册审核通过", shop);
    }

    @Override
    public Result notPassShop(Shop shop) {
        shopMapper.deleteById(shop.getId());
        return new Result(200, "商店注册审核不通过", shop);
    }

    @Override
    public Result deleteShop(Shop shop) {
        String errMessage = "";
        boolean error = false;
        //TOkDO:订单未完成，对活动的处理
        //state转换，视为自动放弃申请活动
        shop.setState(3);//无资格

        //判断订单，全部已完成、已撤销、已退款才可删除
        List<Category> categoryList = categoryMapper.selectCateByShop(shop.getId());
        for(Category category:categoryList) {
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("category_id", category.getId());
            List<Order> orderList = orderMapper.selectList(wrapper);
            for(Order order:orderList) {
                if(order.getState() <= 2 || order.getState() == 6) {
                    errMessage += "有商品订单存在待支付、待发货、待收货、退款中的一个或多个，不可删除商店\n";
                    error = true;
                    break;
                }
            }
        }

        if(error) {
            shopMapper.updateById(shop);
            return new Result(400,errMessage,"");
        }
        shop.setState(2);//有资格删除
        shopMapper.updateById(shop);
        return new Result(200, "商店删除申请已提交", shop);
    }

    @Override
    public Result passDeShop(Shop shop) {
        if(shop.getState()==3) {
            //申请删除但无资格
            return new Result(400,"商店不满足删除资格",shop);
        }

        //店铺所有商品失效
        List<Category> list = categoryMapper.selectCateByShop(shop.getId());
        for(Category category:list) {
            categoryService.deleteCategory(category);
        }
        //资金转至个人账户
        QueryWrapper<Account> wrapper_ac = new QueryWrapper<>();
        wrapper_ac.eq("ownerid",shop.getOwnerid());
        wrapper_ac.eq("type","shop");
        Account account_shop = accountMapper.selectOne(wrapper_ac);
        wrapper_ac.clear();
        wrapper_ac.eq("ownerid",shop.getOwnerid());
        wrapper_ac.eq("type","user");
        Account account_user = accountMapper.selectOne(wrapper_ac);
        accountService.acTransfer(account_shop, account_user, account_shop.getBalance(), "商店删除");
        //test
        //shop.setState(4);
        //shopMapper.updateById(shop);
        shopMapper.deleteById(shop.getId());
        accountMapper.deleteById(account_shop.getId());
        return new Result(200, "审核通过商店删除申请", shop);
    }

    @Override
    public Result notPassDeShop(Shop shop) {
        shop.setState(1);
        shopMapper.updateById(shop);
        return new Result(200, "审核不通过商店删除申请", shop);
    }



}

