package com.example.backend;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.*;
import com.example.backend.mapper.*;
import com.example.backend.service.AccountService;
import com.example.backend.service.ShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    TransferMapper transferMapper;

    @Autowired
    ShopService shopService;
    @Autowired
    AccountService accountService;

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ShoppingCartMapper shoppingCartMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    SubsidyMapper subsidyMapper;

    @Test
    @DisplayName("插入数据")
    public void testInsert(){
        //User user=new User("user","a","123","1232142","test1@qq.com","1234");
        //Integer id=userMapper.insert(user);
        Shop shop = new Shop("12","drink","16","abab","jasjkdjal","1299.9","3-4");
        Integer id = shopMapper.insert(shop);
        System.out.printf(shop.getId().toString());
        //shopService.addShop(shop,16);

    }

    @Test
    @DisplayName("根据id查找")
    public void testSelectById(){
        User user=userMapper.selectById(1);
        System.out.println(user.toString());
    }

    @Test
    @DisplayName("查找所有")
    public void testSelectAll(){
        List userList=userMapper.selectObjs(null);
        System.out.println(userList.size());
    }

    @Test
    @DisplayName("更新")
    public void testUpdate(){
        User user=new User();
        user.setId(1);
        user.setPhone("000");
        Integer id=userMapper.updateById(user);
        System.out.println(id);
    }

    @Test
    @DisplayName("删除")
    public void testDelete(){
        userMapper.deleteById(3);
    }

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("转账流水")
    public void testTransfer() {
        //3给4转100
        Account account1 = accountMapper.selectById(3);
        Account account2 = accountMapper.selectById(4);
        float money = 100;

        //TOkDO: msg & time 属性添加
        accountService.acTransfer(account1,account2,money,"test");
    }

    @Test
    @DisplayName("删除商品")
    public void deleteCategory() {
        Category category = new Category("b","bbb","sjkj",3,8,2);
        category.setId(3);
        category.setState(2);
        categoryMapper.updateById(category);
        //下架时，购物车对应商品失效
        List<ShoppingCart> list = shoppingCartMapper.selectShoppingCartByCate(category.getId());
        for(ShoppingCart shoppingCart:list) {
            shoppingCart.setCatestate(2);
            shoppingCartMapper.updateById(shoppingCart);
        }
        //return new Result(200,"该商品已下架",category);
    }

    @Test
    @DisplayName("查询流水")
    public void selectTransfer() {
        Integer id = 4;
        Page<Transfer> page = new Page<>(0,0);
        transferMapper.selectTransferByID(page,id);
        transferMapper.selectTransferWeek(page,id);
    }

    @Test
    @DisplayName("查询订单")
    public void selectOrder() {
        Integer id = 1;
        Page<Order> page = new Page<>(0,100);
        IPage<Order> iPage = orderMapper.selectOrderByShop(page,id);
        System.out.println(iPage.getRecords().size());
        //System.out.println(iPage.getRecords().get(0).getId());
    }

    @Test
    @DisplayName("添加订单")
    public void addOrder() {
        //test.setState(0);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        Order test = new Order(6,6,6,6,time);
        //test.setBuy_time(time);
        //test.setOwnerid(6);
        Integer i = orderMapper.insert(test);
        System.out.println(i);
        System.out.println(test.getId());
    }

    @Test
    @DisplayName("查询活动商店商品")
    public void selectSubsidyShopCate() {
        List<Category> list = subsidyMapper.selectSubsidyCate(1);
        System.out.println(list.size());
    }

    @Test
    @DisplayName("java除法测试")
    public void divideIntFloat() {
        Integer a = 3;
        float b = 7;
        System.out.println((int) a/b);      //0.42857143
        System.out.println((float)a/b);     //0.42857143
        System.out.println((int)b/a);       //2
        System.out.println((float) b/a);    //2.3333333
    }

}
