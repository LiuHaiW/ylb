package com.liu.ylb.front.settings;

import com.liu.ylb.front.interceptors.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 *  WebMvcConfigurer 相当于springmvc配置文件
 */
@Configuration
public class WebMvcSettings implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建拦截器对象
        TokenInterceptor tokenInterceptor = new TokenInterceptor(stringRedisTemplate);

        //配置拦截器的拦截地址
        String[] addPath = {"/user/realName","/user/loginUp"};
        registry.addInterceptor(tokenInterceptor).addPathPatterns(addPath);
    }
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
