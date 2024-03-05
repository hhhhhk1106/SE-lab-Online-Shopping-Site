package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.Result;
import com.example.backend.entity.CateRecord;
import com.example.backend.entity.QueryDTO;

public interface CateRecordService {
    //分页展示商品上架申请
    public IPage<CateRecord> selectCateRegisterPage(QueryDTO queryDTO);
    //分页展示商品修改申请
    public IPage<CateRecord> selectCateUpdaterPage(QueryDTO queryDTO);
    //分页展示单个商户发出的申请
    public IPage<CateRecord> selectCateOwnerPage(QueryDTO queryDTO,Integer ownerid);
    //分页展示商品活动申请
    public IPage<CateRecord> selectCateSubsidyPage(QueryDTO queryDTO);

    //通过申请记录（上架、修改、活动）
    public Result passCateRecord(CateRecord cateRecord);
    //不通过申请记录（上架、修改、活动）
    public Result notPassCaterRecord(CateRecord cateRecord);

}
