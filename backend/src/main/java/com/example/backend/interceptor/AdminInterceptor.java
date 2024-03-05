package com.example.backend.interceptor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.backend.interceptor.LoginInterceptor.setReturn;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    @CrossOrigin
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, ServletException {
//        String token = request.getHeader("token");
//        if(StringUtils.isBlank(token)) {
//            setReturn(response, 401, "用户未登录，请先登录");
//            return false;
//        }
        String role = (String) request.getSession().getAttribute("role");
        if(!role.equals("admin")) {
            setReturn(response,401,"您不是管理员，没有权限访问此页面");
            return false;
        }
        System.out.println("成功");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
