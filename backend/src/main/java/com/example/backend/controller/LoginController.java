package com.example.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.Result;
import com.example.backend.entity.LoginDTO;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    HttpServletRequest request;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/api/login")
    @CrossOrigin
    public Result login(@RequestBody LoginDTO loginDTO) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("username", loginDTO.getUsername());
        User user = userMapper.selectOne(wrapper);

        request.getSession().setAttribute("role",user.getRole());
        request.getSession().setAttribute("id",user.getId());
        request.getSession().setAttribute("username",user.getUsername());
        return loginService.login(loginDTO);
    }
}
