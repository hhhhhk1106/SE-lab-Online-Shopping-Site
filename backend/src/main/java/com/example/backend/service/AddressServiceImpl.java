package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.Result;
import com.example.backend.entity.Address;
import com.example.backend.entity.QueryDTO;
import com.example.backend.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressMapper addressMapper;
    @Override
    public IPage<Address> selectAddressByOwnerId(QueryDTO queryDTO, Integer id) {
        Page<Address> page = new Page<>(queryDTO.getPageNo(), queryDTO.getPageSize());
        return addressMapper.selectAddressById(page, id);
    }

    @Override
    public Result addAddress(Address address, Integer id) {
        address.setOwnerid(id);
        addressMapper.insert(address);
        return new Result(200, "收货地址添加成功", address);
    }

    @Override
    public Result updateAddress(Address address) {
        addressMapper.updateById(address);
        return new Result(200,"收货地址修改成功", address);
    }

    @Override
    public Result deleteAddress(Address address) {
        addressMapper.deleteById(address.getId());
        return new Result(200,"收货地址删除成功", address);
    }

    @Override
    public Address selectAddressById(String id_str) {
        Integer id = Integer.parseInt(id_str);
        Address address = addressMapper.selectById(id);
        return address;
    }
}
