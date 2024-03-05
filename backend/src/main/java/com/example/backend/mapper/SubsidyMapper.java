package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Category;
import com.example.backend.entity.Shop;
import com.example.backend.entity.Subsidy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubsidyMapper extends BaseMapper<Subsidy> {
    //展示所有活动
    IPage<Subsidy> selectSubsidyPage(Page<Subsidy> page);
    //展示所有正在进行的活动
    IPage<Subsidy> selectSubsidyNowPage(Page<Subsidy> page);
    List<Subsidy> checkSubsidy();
    List<Shop> selectSubsidyShop(Integer subsidy_id);
    List<Category> selectSubsidyCate(Integer subsidy_id);
}
