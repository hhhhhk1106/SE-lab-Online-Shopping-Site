package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    //TOkDO:test
    //展示商店对应的商品
    IPage<Category> selectCategoryPage(Page<Category> page, Integer shopID);
    List<Category> selectCateByShop(Integer shopID);
}
