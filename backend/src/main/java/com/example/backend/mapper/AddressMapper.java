package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {
    IPage<Address> selectAddressById(Page<Address> page, Integer ownerid);
}
