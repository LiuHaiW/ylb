package com.liu.ylb.front.settings;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  WebMvcConfigurer 相当于springmvc配置文件
 */
@Configuration
public class WebMvcSettings implements WebMvcConfigurer {

    /**
     * 处理全局跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("===========addCorsMappings===========");
        registry.addMapping("/**")  //要处理请求地址， 全部请求地址
                .allowedOriginPatterns("*")  //请求来源的域名。 *表示任意来源
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS") //支持跨域请求的处理方式
                .allowCredentials(true) //服务器支持cookie
                .maxAge(3600)  //预检请求的有效期，3600秒
                .allowedHeaders("*");  //允许在跨域请求中的出现header名称
    }
}
