package com.example.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.Result;
import com.example.backend.entity.LoginDTO;
import com.example.backend.entity.LoginVO;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(LoginDTO loginDTO) {
        String msg = "";
        boolean error = false;
        User user = new User();

        if(StringUtils.isEmpty(loginDTO.getUsername())) {
            msg = "用户名不能为空";
            error = true;
            //return new Result(400, "用户名不能为空","");
        }else if(StringUtils.isEmpty(loginDTO.getPassword())) {
            msg = "密码不能为空";
            error = true;
            //return new Result(400, "密码不能为空", "");
        }else{
            // 通过用户名查询 比较密码 (got from web)
            QueryWrapper<User> wrapper = new QueryWrapper();
            wrapper.eq("username", loginDTO.getUsername());
            user = userMapper.selectOne(wrapper);
            if(user==null || !user.getPassword().equals(loginDTO.getPassword())) {
                msg = "登录失败，请检查用户名或密码";
                error = true;
                //return new Result(400, "登录失败，请检查用户名或密码", "");
            }
        }
        if(error) {
            return new Result(400, msg, "");
        }
        //预留添加拦截器以后相关代码
        LoginVO loginVO = new LoginVO();
        loginVO.setId(user.getId());
        //loginVO.setToken(UUID.randomUUID().toString());
        loginVO.setToken(user.getRole());
        loginVO.setUser(user);
        return new Result(200, "", loginVO);
        //return new Result(200, "", user);

    }
}
