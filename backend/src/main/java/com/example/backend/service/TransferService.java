package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.Transfer;

import java.util.List;

public interface TransferService {
    public IPage<Transfer> selectTransferPage(QueryDTO queryDTO);
    public IPage<Transfer> selectTransferByID(QueryDTO queryDTO, Integer id);
    public IPage<Transfer> selectTransferWeek(QueryDTO queryDTO, Integer id);
    public IPage<Transfer> selectTransferMonth(QueryDTO queryDTO, Integer id);
    public float profitOfTransfer(List<Transfer> transferList, Integer id);
    public List<Transfer> selectTransferListByID(Integer id);
    public List<Transfer> selectTransferListByIDWeek(Integer id);
    public List<Transfer> selectTransferListByIDMonth(Integer id);
}
