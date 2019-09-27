package com.aiyi.disk.disk.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gsk
 * 拦截器配置
 */
@Configuration
public class InterceptorConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new RequestThreadFilterConf());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("images/**");
        registration.excludePathPatterns("libs/**");
        registration.excludePathPatterns("*.map");
        registration.excludePathPatterns("/error");
    }

}
