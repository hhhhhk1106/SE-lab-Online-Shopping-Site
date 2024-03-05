package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.Result;
import com.example.backend.entity.Account;
import com.example.backend.entity.QueryDTO;

public interface AccountService {
    public IPage<Account> selectAccountPage(QueryDTO queryDTO, Integer id);
    public Result addAccount(Account account);
    //public Result acRecharge(Account account, float money);//充值
    public Result acRecharge(Integer ownerid, float money);//充值
    public Result acTransfer(Account acOut, Account acIn, float money, String message);//转账

}
