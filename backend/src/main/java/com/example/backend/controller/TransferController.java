package com.example.backend.controller;

import com.example.backend.Result;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.Transfer;
import com.example.backend.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransferController {
    @Autowired
    private TransferService transferService;
    //分页展示转账流水
    @RequestMapping("/api/transfer_list")
    @CrossOrigin
    public Result transList(@RequestBody QueryDTO queryDTO) {
        return new Result(200,"",transferService.selectTransferPage(queryDTO));
    }

    //分页展示账户转账流水
    @RequestMapping("/api/transfer_list_id")
    @CrossOrigin
    public Result transListByID(@RequestBody QueryDTO queryDTO, @RequestParam(value = "id")Integer id) {
        return new Result(200,"",transferService.selectTransferByID(queryDTO, id));
    }
    //分页展示账户近一周转账流水
    @RequestMapping("/api/transfer_list_id_week")
    @CrossOrigin
    public Result transListByIDWeek(@RequestBody QueryDTO queryDTO, @RequestParam(value = "id")Integer id) {
        return new Result(200,"",transferService.selectTransferWeek(queryDTO, id));
    }
    //分页展示账户近一月转账流水
    @RequestMapping("/api/transfer_list_id_month")
    @CrossOrigin
    public Result transListByIDMonth(@RequestBody QueryDTO queryDTO, @RequestParam(value = "id")Integer id) {
        return new Result(200,"",transferService.selectTransferMonth(queryDTO, id));
    }

    //账户转账流水利润
    @RequestMapping("/api/transfer_profit_id")
    @CrossOrigin
    public float transProfitByID(@RequestParam(value = "id")Integer id) {
        List<Transfer> list = transferService.selectTransferListByID(id);
        return transferService.profitOfTransfer(list, id);
    }
    //账户近一周转账流水利润
    @RequestMapping("/api/transfer_profit_id_week")
    @CrossOrigin
    public float transProfitByIDWeek(@RequestParam(value = "id")Integer id) {
        List<Transfer> list = transferService.selectTransferListByIDWeek(id);
        return transferService.profitOfTransfer(list, id);
    }
    //账户近一月转账流水利润
    @RequestMapping("/api/transfer_profit_id_month")
    @CrossOrigin
    public float transProfitByIDMonth(@RequestParam(value = "id")Integer id) {
        List<Transfer> list = transferService.selectTransferListByIDMonth(id);
        return transferService.profitOfTransfer(list, id);
    }
}
