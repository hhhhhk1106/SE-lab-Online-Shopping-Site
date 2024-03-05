package com.example.backend.controller;

import com.example.backend.Result;
import com.example.backend.entity.Category;
import com.example.backend.entity.QueryDTO;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/user_list")
    public Result userList(@RequestBody QueryDTO queryDTO){
        return new Result(200,"",userService.selectUserPage(queryDTO));
    }

    @RequestMapping("/api/user_info")
    @CrossOrigin
    public Result searchByName(@RequestBody String name) {
        User user = userService.searchByName(name);
        if(user == null) {
            return new Result(400,"用户不存在","");
        }
        return new Result(200,"",user);
    }

    //通过用户id返回信息
    @RequestMapping("/api/user_info_id")
    @CrossOrigin
    public Result searchUserByID(@RequestBody String id_str) {
        User user = userService.searchByID(id_str);
        if(user == null) {
            return new Result(400,"用户不存在","");
        }
        return new Result(200,"",user);
    }

    @RequestMapping("/api/user_register")
    @CrossOrigin
    public Result addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @RequestMapping("/api/user_update")
    @CrossOrigin
    public Result updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @RequestMapping("/api/user_delete")
    @CrossOrigin
    public Result deleteUser(Integer id){
        return new Result(200,"",userService.deleteUser(id));
    }

    @PostMapping("/api/user/delete/batch")
    public Result batchDeleteUser(@RequestBody List<Integer> ids){
        userService.batchDelete(ids);
        return new Result(200,"","");
    }

}