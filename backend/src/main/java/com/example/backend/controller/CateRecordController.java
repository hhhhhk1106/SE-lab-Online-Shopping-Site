package com.example.backend.controller;

import com.example.backend.Result;
import com.example.backend.entity.QueryDTO;
import com.example.backend.service.CateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CateRecordController {
    @Autowired
    private CateRecordService cateRecordService;

    @RequestMapping("/api/cate_record_register")
    @CrossOrigin
    public Result cateRegisterList(@RequestBody QueryDTO queryDTO) {
        return new Result(200,"",cateRecordService.selectCateRegisterPage(queryDTO));
    }

    @RequestMapping("/api/cate_record_update")
    @CrossOrigin
    public Result cateUpdateList(@RequestBody QueryDTO queryDTO) {
        return new Result(200,"",cateRecordService.selectCateUpdaterPage(queryDTO));
    }

    @RequestMapping("/api/cate_record_subsidy")
    @CrossOrigin
    public Result cateSubsidyList(@RequestBody QueryDTO queryDTO) {
        return new Result(200,"",cateRecordService.selectCateSubsidyPage(queryDTO));
    }

}
