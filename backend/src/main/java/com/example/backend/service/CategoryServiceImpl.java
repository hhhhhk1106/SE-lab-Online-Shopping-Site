package com.example.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.Result;
import com.example.backend.entity.*;
import com.example.backend.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private CateRecordMapper cateRecordMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public IPage<Category> selectCatePageByShop(QueryDTO queryDTO, Integer shopID) {
        Page<Category> page = new Page<>(queryDTO.getPageNo(), queryDTO.getPageSize());
        return categoryMapper.selectCategoryPage(page, shopID);
    }


    @Override
    public Result addCategory(Category category, Integer ownerid) {
        String errMessage = "";
        boolean error = false;
        //设置店主、商店id
        category.setOwnerid(ownerid);
        QueryWrapper<Shop> wrapper = new QueryWrapper<>();
        wrapper.eq("ownerid", ownerid);
        Shop shop = shopMapper.selectOne(wrapper);
        category.setShopID(shop.getId());
        //TODO:大于0的浮点数


        if(error) {
            return new Result(400,errMessage,"");
        }
        //TODO:上架申请记录ok,test id
        categoryMapper.insert(category);
        Integer id = category.getId();
        CateRecord cateRecord = new CateRecord(ownerid,"register",id);
        cateRecordMapper.insert(cateRecord);

        return new Result(200,"商品上架申请已提交",category);
    }

    @Override
    public Result updateCategory(Category newCategory,Integer oldID,Integer ownerid) {
        String errMessage = "";
        boolean error = false;
        //TOkDO:状态判断，活动？

        //申请修改记录
        //旧商品状态改为修改申请中
        Category oldCategory = categoryMapper.selectById(oldID);

        if(oldCategory.getState() == 6) {   //申请活动中
            errMessage += "该商品正在申请活动，不可修改\n";
            error = true;
        }
        if(error) {
            return new Result(400,errMessage,"");
        }

        oldCategory.setState(3);    //申请修改中
        categoryMapper.updateById(oldCategory);

        newCategory.setState(4);
        categoryMapper.insert(newCategory);
        CateRecord cateRecord = new CateRecord(ownerid, "update",newCategory.getId(),oldID);
        cateRecordMapper.insert(cateRecord);

        return new Result(200,"商品修改申请已提交",cateRecord);
    }

    @Override
    public Result deleteCategory(Category category) {
        String errMessage = "";
        boolean error = false;
        //TOkDO: 待支付、发货订单中有，不给下架
        // 订单与商品失效的处理
        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("category_id", category.getId());
        List<Order> orderList = orderMapper.selectList(orderWrapper);
        for(Order order:orderList) {
            if(order.getState() <= 1) { //待支付、待发货
                errMessage += "在待支付、待发货订单中有该商品，不可下架\n";
                error = true;
                break;
            }
        }

        if(error) {
            return new Result(400,errMessage,"");
        }

        category.setState(5);
        categoryMapper.updateById(category);
        //下架时，购物车对应商品失效
        List<ShoppingCart> list = shoppingCartMapper.selectShoppingCartByCate(category.getId());
        for(ShoppingCart shoppingCart:list) {
            //System.out.println(category.getId());
            shoppingCart.setCatestate(2);
            shoppingCartMapper.updateById(shoppingCart);
        }
        //对应商品管理记录变为失效
        QueryWrapper<CateRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("old_category_id", category.getId());
        List<CateRecord> cateRecordList = cateRecordMapper.selectList(wrapper);
        for(CateRecord cateRecord:cateRecordList) {
            cateRecord.setState(3);
            cateRecordMapper.updateById(cateRecord);
        }
        wrapper.clear();
        wrapper.eq("new_category_id", category.getId());
        cateRecordList = cateRecordMapper.selectList(wrapper);
        for(CateRecord cateRecord:cateRecordList) {
            cateRecord.setState(3);
            cateRecordMapper.updateById(cateRecord);
        }

        return new Result(200,"该商品已下架",category);
    }

    @Override
    public Category searchByID(String id_str) {
        Integer id = Integer.parseInt(id_str);
        Category category = categoryMapper.selectById(id);
        return category;
    }

    @Override
    public Result applySubsidyCate(Category category) {
        String errMessage = "";
        boolean error = false;

        //TODO:仅正常商品可以申请活动 √, *判断类别?
        //商品状态不改变，仅增加record
        Shop shop = shopMapper.selectById(category.getShopID());
        if(shop.getSubsidy_id() == 0 || shop.getState() == 4) {
            errMessage += "商店没有正在进行的活动\n";
            error = true;
        }
        if(category.getState() != 1) {
            errMessage += "商品状态不支持申请活动\n";
            error = true;
        }

        if(error) {
            return new Result(400,errMessage,"");
        }
        category.setState(6);
        categoryMapper.updateById(category);
        CateRecord cateRecord = new CateRecord(category.getOwnerid(), "subsidy",category.getId());
        cateRecordMapper.insert(cateRecord);
        return new Result(200,"商品活动申请已提交",cateRecord);
    }

}
