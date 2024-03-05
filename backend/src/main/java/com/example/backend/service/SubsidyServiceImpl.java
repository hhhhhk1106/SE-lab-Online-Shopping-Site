package com.example.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.Result;
import com.example.backend.entity.*;
import com.example.backend.mapper.AccountMapper;
import com.example.backend.mapper.CategoryMapper;
import com.example.backend.mapper.ShopMapper;
import com.example.backend.mapper.SubsidyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubsidyServiceImpl implements SubsidyService{
    @Autowired
    private SubsidyMapper subsidyMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountService accountService;
    @Override
    public Subsidy searchByID(String id_str) {
        Integer id = Integer.parseInt(id_str);
        Subsidy subsidy = subsidyMapper.selectById(id);
        return subsidy;
    }

    @Override
    public IPage<Subsidy> selectSubsidyAll(QueryDTO queryDTO) {
        Page<Subsidy> page = new Page<>(queryDTO.getPageNo(), queryDTO.getPageSize());
        return subsidyMapper.selectSubsidyPage(page);
    }

    @Override
    public IPage<Subsidy> selectSubsidyNow(QueryDTO queryDTO) {
        Page<Subsidy> page = new Page<>(queryDTO.getPageNo(), queryDTO.getPageSize());
        return subsidyMapper.selectSubsidyNowPage(page);
    }

    @Override
    public Result addSubsidy(Subsidy subsidy) {
        subsidyMapper.insert(subsidy);
        return new Result(200,"补贴活动已设置",subsidy);
    }

    @Override
    public Result updateSubsidy(Subsidy subsidy) {
        subsidyMapper.updateById(subsidy);
        return new Result(200,"补贴活动修改成功",subsidy);
    }

    @Override
    public Result openSubsidy(Subsidy subsidy) {
        //TOkDO:开启的判断、转账
        String errMessage = "";
        Boolean error = false;

        QueryWrapper<Account> wrapper_ac = new QueryWrapper<>();
        wrapper_ac.eq("type","profit");
        Account account_profit = accountMapper.selectOne(wrapper_ac);

        if(account_profit.getBalance() < subsidy.getFund()) {//余额不足
            errMessage += "利润账户余额不足，开启失败\n";
            error = true;
        }
        if(error) {
            return new Result(400,errMessage,"");
        }
        wrapper_ac.clear();
        wrapper_ac.eq("type","intermediate");
        Account account_inter = accountMapper.selectOne(wrapper_ac);
        accountService.acTransfer(account_profit, account_inter, subsidy.getFund(), "开启活动");

        subsidy.setState(1);    //进行中
        subsidyMapper.updateById(subsidy);
        return new Result(200,"补贴活动已开启",subsidy);
    }

    @Override
    public void checkSubsidyFund(Subsidy subsidy) {
        if(subsidy.getFund() < subsidy.getDiscount()) {
            subsidy.setState(3);    //余额不足
            refreshSubsidy(subsidy);
            subsidyMapper.updateById(subsidy);
        }
    }

    //TOkDO:活动结束更新商店、商品subsidy_id
    @Override
    public void refreshSubsidy(Subsidy subsidy) {
        List<Shop> shopList = subsidyMapper.selectSubsidyShop(subsidy.getId());
        List<Category> categoryList = subsidyMapper.selectSubsidyCate(subsidy.getId());
        for (Shop shop:shopList) {
            shop.setSubsidy_id(0);
            shopMapper.updateById(shop);
        }
        for (Category category:categoryList) {
            category.setSubsidy_id(0);
            categoryMapper.updateById(category);
        }
    }

    @Override
    public List<Category> showSubsidyCategories(QueryDTO queryDTO, Integer id) {
        List<Category> categoryList = subsidyMapper.selectSubsidyCate(id);
        return categoryList;
    }

    @Override
    public List<Shop> showSubsidyShops(QueryDTO queryDTO, Integer id) {
        List<Shop> shopList = subsidyMapper.selectSubsidyShop(id);
        return shopList;
    }
}
