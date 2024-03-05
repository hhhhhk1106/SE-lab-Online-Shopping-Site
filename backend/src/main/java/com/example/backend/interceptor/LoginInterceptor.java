package com.example.backend.interceptor;

import com.example.backend.Result;
import com.example.backend.util.HttpContextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    @CrossOrigin
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, ServletException {

//        String token = request.getHeader("token");
//        if(StringUtils.isBlank(token)) {
//            setReturn(response, 401, "用户未登录，请先登录");
//            return false;
//        }
        String errMessage = "";
        boolean error = false;

        String role = (String) request.getSession().getAttribute("role");
        if(role == null) {
            errMessage = "用户未登录";
            error = true;
//            setReturn(response, 401, "用户未登录");
//            return false;
        }

        String username = (String) request.getSession().getAttribute("username");
        if(username == null) {
            errMessage = "不能取得用户名";
            error = true;
//            setReturn(response, 401, "不能取得用户名");
//            return false;
        }

        if(role!=null&&role.equals("user")) {
            //System.out.println(role);
            errMessage = "您不是商户，没有权限注册";
            error = true;
//            setReturn(response,401,"您不是商户，没有权限注册");
//            return false;
        }

        if(error) {
            setReturn(response,401,errMessage);
            return false;
        }

        //占位（@web 在实际使用中还会:
        // 1、校验token是否能够解密出用户信息来获取访问者
        // 2、token是否已经过期

        System.out.println("成功");
        return true;
    }

    static void setReturn(HttpServletResponse response, Integer code, String msg) throws IOException {
        HttpServletResponse httpResponse = response;
        // got from web
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        httpResponse.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Result result = new Result(code, msg, "");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        httpResponse.getWriter().print(json);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
