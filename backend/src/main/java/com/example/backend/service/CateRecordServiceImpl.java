package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.Result;
import com.example.backend.entity.CateRecord;
import com.example.backend.entity.Category;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.Shop;
import com.example.backend.mapper.CateRecordMapper;
import com.example.backend.mapper.CategoryMapper;
import com.example.backend.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CateRecordServiceImpl implements CateRecordService{
    @Autowired
    private CateRecordMapper cateRecordMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public IPage<CateRecord> selectCateRegisterPage(QueryDTO queryDTO) {
        Page<CateRecord> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return cateRecordMapper.selectCateRegisterPage(page);
    }

    @Override
    public IPage<CateRecord> selectCateUpdaterPage(QueryDTO queryDTO) {
        Page<CateRecord> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return cateRecordMapper.selectCateUpdatePage(page);
    }

    @Override
    public IPage<CateRecord> selectCateOwnerPage(QueryDTO queryDTO,Integer ownerid) {
        Page<CateRecord> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return cateRecordMapper.selectCateOwnerPage(page,ownerid);
    }

    @Override
    public IPage<CateRecord> selectCateSubsidyPage(QueryDTO queryDTO) {
        Page<CateRecord> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return cateRecordMapper.selectCateSubsidyPage(page);
    }

    @Override
    public Result passCateRecord(CateRecord cateRecord) {
        cateRecord.setState(1);
        cateRecordMapper.updateById(cateRecord);
        //TO-DO:test 活动状态继承、sales
        Category newCategory = categoryMapper.selectById(cateRecord.getNew_category_id());
        if(cateRecord.getOperation().equals("register")) {
            newCategory.setState(1);
        }else if(cateRecord.getOperation().equals("update")) {
            categoryMapper.deleteById(newCategory.getId());
            newCategory.setState(1);
            newCategory.setId(cateRecord.getOld_category_id());
        }else if(cateRecord.getOperation().equals("subsidy")) {
            Shop shop = shopMapper.selectById(newCategory.getShopID());
            newCategory.setState(1);
            newCategory.setSubsidy_id(shop.getSubsidy_id());
        }

        categoryMapper.updateById(newCategory);
        return new Result(200,"商品申请审核通过",cateRecord);
    }

    @Override
    public Result notPassCaterRecord(CateRecord cateRecord) {
        cateRecord.setState(2);
        cateRecordMapper.updateById(cateRecord);
        //TODO:test
        Category newCategory = categoryMapper.selectById(cateRecord.getNew_category_id());
        if(cateRecord.getOperation().equals("register")) {
            categoryMapper.deleteById(cateRecord.getNew_category_id());
        }else if(cateRecord.getOperation().equals("update")) {
            newCategory.setState(2);
            categoryMapper.updateById(newCategory);
            Category oldCategory = categoryMapper.selectById(cateRecord.getOld_category_id());
            oldCategory.setState(1);
            categoryMapper.updateById(oldCategory);
        } else if (cateRecord.getOperation().equals("subsidy")) {
            newCategory.setState(1);
            categoryMapper.updateById(newCategory);
        }

        return new Result(200,"商品申请审核不通过",cateRecord);
    }

}
