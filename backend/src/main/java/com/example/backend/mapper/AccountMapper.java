package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    IPage<Account> selectAccountPage(Page<Account> page, Integer id);
}
