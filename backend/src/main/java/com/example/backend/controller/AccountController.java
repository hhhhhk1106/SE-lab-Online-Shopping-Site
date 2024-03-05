package com.example.backend.controller;

import com.example.backend.Result;
import com.example.backend.entity.QueryDTO;
import com.example.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("/api/account_list")
    @CrossOrigin
    public Result accountList(@RequestBody QueryDTO queryDTO, @RequestParam(value = "id")Integer id) {
        return new Result(200,"",accountService.selectAccountPage(queryDTO, id));
    }

    //id为账户id
    @RequestMapping("/api/account_recharge")
    @CrossOrigin
    public Result acRechage(@RequestBody String money_str, @RequestParam(value = "id")Integer id) {
        float money = Float.parseFloat(money_str);
        return accountService.acRecharge(id,money);
    }

//    @RequestMapping("/api/account_transfer")
//    @CrossOrigin
//    public Result acTransfer(@RequestBody Account account) {
//        return
//    }

}
