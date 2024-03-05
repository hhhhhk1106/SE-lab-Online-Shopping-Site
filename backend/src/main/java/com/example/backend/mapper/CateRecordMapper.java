package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.CateRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CateRecordMapper extends BaseMapper<CateRecord> {
    IPage<CateRecord> selectCateRegisterPage(Page<CateRecord> page);
    IPage<CateRecord> selectCateUpdatePage(Page<CateRecord> page);
    IPage<CateRecord> selectCateOwnerPage(Page<CateRecord> page, Integer ownerid);
    IPage<CateRecord> selectCateSubsidyPage(Page<CateRecord> page);
}
