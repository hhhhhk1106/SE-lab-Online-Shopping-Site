package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.Transfer;
import com.example.backend.mapper.TransferMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl implements TransferService{
    @Autowired
    private TransferMapper transferMapper;

    @Override
    public IPage<Transfer> selectTransferPage(QueryDTO queryDTO) {
        Page<Transfer> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return transferMapper.selectTransferPage(page);
    }

    @Override
    public IPage<Transfer> selectTransferByID(QueryDTO queryDTO, Integer id) {
        Page<Transfer> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return transferMapper.selectTransferByID(page, id);
    }

    @Override
    public IPage<Transfer> selectTransferWeek(QueryDTO queryDTO, Integer id) {
        Page<Transfer> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return transferMapper.selectTransferWeek(page, id);
    }

    @Override
    public IPage<Transfer> selectTransferMonth(QueryDTO queryDTO, Integer id) {
        Page<Transfer> page = new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return transferMapper.selectTransferMonth(page, id);
    }

    @Override
    public List<Transfer> selectTransferListByID(Integer id) {
        return transferMapper.selectTransferListByID(id);
    }

    @Override
    public List<Transfer> selectTransferListByIDWeek(Integer id) {
        return transferMapper.selectTransferListByIDWeek(id);
    }

    @Override
    public List<Transfer> selectTransferListByIDMonth(Integer id) {
        return transferMapper.selectTransferListByIDMonth(id);
    }

    @Override
    public float profitOfTransfer(List<Transfer> transferList, Integer id) {
        float profit = 0;
        for(Transfer transfer:transferList) {
            if (transfer.getAccount_in_id() == id) {
                profit += transfer.getMoney();
            }
            if (transfer.getAccount_out_id() == id) {
                profit -= transfer.getMoney();
            }
        }
        return profit;
    }
}
