package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Transfer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransferMapper extends BaseMapper<Transfer> {
    IPage<Transfer> selectTransferPage(Page<Transfer> page);
    IPage<Transfer> selectTransferByID(Page<Transfer> page, Integer id);
    IPage<Transfer> selectTransferWeek(Page<Transfer> page, Integer id);
    IPage<Transfer> selectTransferMonth(Page<Transfer> page, Integer id);
    List<Transfer> selectTransferListByID(Integer id);
    List<Transfer> selectTransferListByIDWeek(Integer id);
    List<Transfer> selectTransferListByIDMonth(Integer id);
}
