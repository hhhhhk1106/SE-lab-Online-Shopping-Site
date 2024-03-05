package com.example.backend.controller;

import com.example.backend.Result;
import com.example.backend.entity.Address;
import com.example.backend.entity.QueryDTO;
import com.example.backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    //通过地址id返回信息
    @RequestMapping("/api/address_info_id")
    @CrossOrigin
    public Result searchAddressByID(@RequestBody String id_str) {
        Address address = addressService.selectAddressById(id_str);
        if(address == null) {
            return new Result(400,"地址不存在","");
        }
        return new Result(200, "", address);
    }

    //通过用户id返回其拥有的收货地址
    @RequestMapping("/api/address_show_id")
    @CrossOrigin
    public Result searchAddressByOwnerID(@RequestBody QueryDTO queryDTO, HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        if(id == null) {
            return new Result(400,"用户未登录","");
        }
        return new Result(200, "", addressService.selectAddressByOwnerId(queryDTO, id));
    }

    //添加收货地址
    @RequestMapping("/api/address_add")
    @CrossOrigin
    public Result addAddress(@RequestBody Address address, HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        if(id == null) {
            return new Result(400,"用户未登录","");
        }
        return new Result(200, "", addressService.addAddress(address, id));
    }

    //修改收货地址
    @RequestMapping("/api/address_update")
    @CrossOrigin
    public Result updateAddress(@RequestBody Address address) {
        return new Result(200, "", addressService.updateAddress(address));
    }

    //删除收货地址
    @RequestMapping("/api/address_delete")
    @CrossOrigin
    public Result deleteAddress(@RequestBody Address address) {
        return new Result(200, "", addressService.deleteAddress(address));
    }
}
