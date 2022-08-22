package com.liu.ylb;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwaggerBootstrapUI
@EnableSwagger2
@MapperScan("com.liu.ylb.db.mapper")
@SpringBootApplication
public class YinglibaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(YinglibaoApplication.class, args);
    }

}
