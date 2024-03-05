package com.example.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.Result;
import com.example.backend.entity.Account;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.Transfer;
import com.example.backend.mapper.AccountMapper;
import com.example.backend.mapper.TransferMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TransferMapper transferMapper;

    @Override
    public IPage<Account> selectAccountPage(QueryDTO queryDTO, Integer id) {
        Page<Account> page = new Page<>(queryDTO.getPageNo(), queryDTO.getPageSize());
        return accountMapper.selectAccountPage(page, id);
    }

    //接口函数，实际上添加账户功能写在了注册功能里
    @Override
    public Result addAccount(Account account) {
        accountMapper.insert(account);
        return new Result(200, "账户添加成功", account);
    }

//    @Override
//    public Result acRecharge(Account account, float money) {
//        account.recharge(money);
//        accountMapper.updateById(account);
//        return new Result(200, "充值成功", account);
//    }

    @Override
    public Result acRecharge(Integer id, float money) {
        Account account = accountMapper.selectById(id);
        if(account.getType().equals("shop")) {
            QueryWrapper<Account> wrapper_owner = new QueryWrapper<>();
            wrapper_owner.eq("ownerid",account.getOwnerid());
            wrapper_owner.eq("type","user");
            Account account_owner = accountMapper.selectOne(wrapper_owner);
            if(account_owner.getBalance()<money) {
                return new Result(400, "充值失败，个人账户余额不足", account_owner);
            }
            account_owner.recharge(0-money);
            accountMapper.updateById(account_owner);
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            transferMapper.insert(new Transfer(account.getId(),account_owner.getId(),money,"商户给商店充值",timestamp));
        }
        account.recharge(money);
        accountMapper.updateById(account);
        if(!account.getType().equals("shop")) { //包含用户、中间账户
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            transferMapper.insert(new Transfer(account.getId(),0,money,"用户充值",timestamp));
        }
        return new Result(200, "充值成功", account);
    }

    @Override
    public Result acTransfer(Account acOut, Account acIn, float money, String message) {
        String errMessage = "";
        Boolean error = false;

        if(money < 0) {
            errMessage += "转账金额为负\n";
            error = true;
        }
        if(acOut == null) {
            errMessage += "缺少出口账户\n";
            error = true;
        }else if(acOut.getBalance() < money) {
            errMessage += "转账失败，余额不足\n";
            error = true;
        }
        if(error) {
            return new Result(400,errMessage,"");
        }

        acIn.recharge(money);
        acOut.recharge(0-money);
        accountMapper.updateById(acIn);
        accountMapper.updateById(acOut);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        transferMapper.insert(new Transfer(acIn.getId(),acOut.getId(),money,message,timestamp));

        return new Result(200, "转账成功", acOut);
    }
}
