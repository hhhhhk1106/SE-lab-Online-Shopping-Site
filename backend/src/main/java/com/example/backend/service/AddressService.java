package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.Result;
import com.example.backend.entity.Address;
import com.example.backend.entity.QueryDTO;

public interface AddressService {
    public IPage<Address> selectAddressByOwnerId(QueryDTO queryDTO, Integer id);
    public Result addAddress(Address address, Integer id);
    public Result updateAddress(Address address);
    public Result deleteAddress(Address address);
    public Address selectAddressById(String id_str);
}
