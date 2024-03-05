package com.example.backend.config;

import com.example.backend.interceptor.AdminInterceptor;
import com.example.backend.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//拦截器配置
@Configuration
public class DemoWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //可添加多个
        //仅仅排除商店页面还是会被拦截
        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/main_page")
                .addPathPatterns("/api/store_register")
                .excludePathPatterns("/api/user_register");
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/api/store_new_list");
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
}
