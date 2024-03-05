package com.example.backend.service;

import com.example.backend.Result;
import com.example.backend.entity.LoginDTO;

//@Service
public interface LoginService {
    public Result login(LoginDTO loginDTO);
}
