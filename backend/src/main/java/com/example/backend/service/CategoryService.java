package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.Result;
import com.example.backend.entity.Category;
import com.example.backend.entity.QueryDTO;

public interface CategoryService {
    public IPage<Category> selectCatePageByShop(QueryDTO queryDTO, Integer shopID);
    public Result addCategory(Category category, Integer ownerid);
    public Result updateCategory(Category category,Integer oldID,Integer ownerid);
    public Result deleteCategory(Category category);
    public Category searchByID(String id);
    public Result applySubsidyCate(Category category);
}
