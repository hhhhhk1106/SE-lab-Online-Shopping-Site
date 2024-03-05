package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.Result;
import com.example.backend.entity.Category;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.Shop;
import com.example.backend.entity.Subsidy;

import java.util.List;

public interface SubsidyService {
    public Subsidy searchByID(String id_str);
    public IPage<Subsidy> selectSubsidyAll(QueryDTO queryDTO);
    public IPage<Subsidy> selectSubsidyNow(QueryDTO queryDTO);
    public Result addSubsidy(Subsidy subsidy);
    public Result updateSubsidy(Subsidy subsidy);
    public Result openSubsidy(Subsidy subsidy);
    public void checkSubsidyFund(Subsidy subsidy);
    public void refreshSubsidy(Subsidy subsidy);
    public List<Category> showSubsidyCategories(QueryDTO queryDTO, Integer id);
    public List<Shop> showSubsidyShops(QueryDTO queryDTO, Integer id);
}
