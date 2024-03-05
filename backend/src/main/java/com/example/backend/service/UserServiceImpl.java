package com.example.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.Result;
import com.example.backend.entity.Account;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.User;
import com.example.backend.mapper.AccountMapper;
import com.example.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public IPage<User> selectUserPage(QueryDTO queryDTO) {
        Page<User> page=new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return userMapper.selectUserPage(page,queryDTO.getKeyword());
    }

    @Override
    public Result addUser(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper();

        String errMessage = "";
        boolean error = false;

        // 通过用户名查询，判断是否已有同名用户
        wrapper.eq("username", user.getUsername());
        User userNew_username = userMapper.selectOne(wrapper);
        if(userNew_username!=null) {
            errMessage += "用户名不能重复\n";
            error = true;
            //return new Result(400, "用户名不能重复", "");
        }

        // 通过身份证号查询，判断是否已有相同身份证号
        wrapper.clear();
        wrapper.eq("idnumber", user.getIdnumber());
        User userNew_idnumber = userMapper.selectOne(wrapper);
        if(userNew_idnumber!=null) {
            errMessage += "身份证号不能重复\n";
            error = true;
            //return new Result(400, "身份证号不能重复", "");
        }

        // 通过邮箱地址查询，判断是否已有相同邮箱地址
        wrapper.clear();
        wrapper.eq("email", user.getEmail());
        User userNew_email = userMapper.selectOne(wrapper);
        if(userNew_email!=null) {
            errMessage += "邮箱地址不能重复\n";
            error = true;
            //return new Result(400, "邮箱地址不能重复", "");
        }

        if(error) {
            return new Result(400, errMessage,"");
        }

        userMapper.insert(user);//insert返回值不能理想实现获取id

        wrapper.eq("username", user.getUsername());
        User user_ac = userMapper.selectOne(wrapper);//获取id
        accountMapper.insert(new Account(user_ac.getId(), "user",0));
        return new Result(200, "", user);
    }

    @Override
    public Result updateUser(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper();

        String errMessage = "";
        boolean error = false;

        // 通过用户名查询，判断是否已有同名用户
        wrapper.eq("username", user.getUsername());
        User userNew_username = userMapper.selectOne(wrapper);
        if(userNew_username!=null && userNew_username.getId()!=user.getId()) {
            errMessage += "用户名不能重复\n";
            error = true;
        }

        // 通过邮箱地址查询，判断是否已有相同邮箱地址
        wrapper.clear();
        wrapper.eq("email", user.getEmail());
        User userNew_email = userMapper.selectOne(wrapper);
        if(userNew_email!=null && userNew_email.getId()!=user.getId()) {
            errMessage += "邮箱地址不能重复\n";
            error = true;
        }

        // 通过电话号码查询，判断是否已有相同电话号码
        wrapper.clear();
        wrapper.eq("phone", user.getPhone());
        User userNew_phone = userMapper.selectOne(wrapper);
        if(userNew_phone!=null && userNew_phone.getId()!=user.getId()) {
            errMessage += "电话号码不能重复\n";
            error = true;
        }

        if(error) {
            return new Result(400, errMessage,"");
        }

        userMapper.updateById(user);
        return new Result(200,"更新成功","");
    }

    @Override
    public Integer deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        userMapper.deleteBatchIds(ids);
    }

    @Override
    public User searchByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", name);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User searchByID(String id_str) {
        Integer id = Integer.parseInt(id_str);
        User user = userMapper.selectById(id);
        return user;
    }
}
