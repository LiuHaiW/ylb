package com.liu.ylb.front.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerSettings {
    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo info = new ApiInfoBuilder().title("盈利宝项目").version("1.0")
                .description("盈利宝前后端分离微服务项目").contact(new Contact("动力节点","http://www.bjpowernode.com","bjpowernode@bjpowenrode.cn")).build();
        docket = docket.apiInfo(info);
        docket = docket.select()
                .apis(RequestHandlerSelectors.basePackage("com.liu.ylb.front.controller")).build();
        return docket;
    }
}
