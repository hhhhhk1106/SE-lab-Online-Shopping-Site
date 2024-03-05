package com.example.backend.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpContextUtil {
    public static String getOrigin() {
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader("Origin");
    }

    public static String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURL().length(), url.length()).toString();
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


}
