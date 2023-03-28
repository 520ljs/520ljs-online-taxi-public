package com.ss.application.interceptor;

import io.lettuce.core.api.push.PushListener;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/7 - 03 - 07 - 21:07
 */
@Configuration// 加入到容器
public class InterceptorConfig implements WebMvcConfigurer {

    // 在拦截器初始化之前。bean已经提前有了，拦截器里面的bean都会一个个注入进去
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())// 添加拦截器
                .addPathPatterns("/**")
                // 不拦截的路径
                .excludePathPatterns("/noauthTest")
                .excludePathPatterns("/verification-code")
                .excludePathPatterns("/verification-code-check")
                .excludePathPatterns("/token-refresh");
    }
}
