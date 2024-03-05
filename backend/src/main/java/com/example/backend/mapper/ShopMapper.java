package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Shop;
import org.apache.ibatis.annotations.Mapper;

//通过审核的店
@Mapper
public interface ShopMapper extends BaseMapper<Shop> {
    IPage<Shop> selectShopPage(Page<Shop> page, String keyword);//一般店铺
    IPage<Shop> selectShopNewPage(Page<Shop> page, String keyword);//新注册
    IPage<Shop> selectShopDeletePage(Page<Shop> page);//申请删除的店铺
    IPage<Shop> selectShopApplyPage(Page<Shop> page);//申请活动的店铺
    IPage<Shop> selectShopPageOwner(Page<Shop> page, Integer ownerid);
}
