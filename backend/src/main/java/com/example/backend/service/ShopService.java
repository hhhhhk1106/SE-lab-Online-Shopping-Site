package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.Result;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.Shop;
import com.example.backend.entity.Subsidy;

public interface ShopService {
    public IPage<Shop> selectShopPage(QueryDTO queryDTO);
    public IPage<Shop> selectShopNewPage(QueryDTO queryDTO);
    public IPage<Shop> selectShopApplyPage(QueryDTO queryDTO);
    //public Result addShop(Shop shop);
    public Result addShop(Shop shop, Integer id);

    //public IPage<ShopNew> selectShopNewPage(QueryDTO queryDTO);

    public Result deleteShop(Shop shop);
    //审核通过申请
    public Result passShop(Shop shop);
    //审核不通过
    public Result notPassShop(Shop shop);
    public Result passDeShop(Shop shop);
    public Result notPassDeShop(Shop shop);
    IPage<Shop> selectShopDeletePage(QueryDTO queryDTO);
    public IPage<Shop> selectShopPageOwner(QueryDTO queryDTO, Integer id);
    public Shop searchShopByID(String id_str);
    public Result applyShopSubsidy(Subsidy subsidy, Integer ownerid);
    public Result passShopSubsidy(Shop shop);
    public Result notPassShopSubsidy(Shop shop);
}
