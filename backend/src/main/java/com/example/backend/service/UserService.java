package com.example.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.backend.Result;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.User;

import java.util.List;

public interface UserService {
    public IPage<User> selectUserPage(QueryDTO queryDTO);
    public Result addUser(User user);//返回用户id
    public Result updateUser(User user);
    public Integer deleteUser(Integer id);
    public void batchDelete(List<Integer> ids);
    public User searchByName(String name);
    public User searchByID(String id_str);
}
