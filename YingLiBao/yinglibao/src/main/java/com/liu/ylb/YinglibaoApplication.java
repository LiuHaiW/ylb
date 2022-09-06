package com.liu.ylb;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.liu.ylb.task.TaskManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableSwaggerBootstrapUI
@EnableSwagger2
@MapperScan("com.liu.ylb.db.mapper")
@SpringBootApplication
public class YinglibaoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(YinglibaoApplication.class, args);
        TaskManager taskManager =(TaskManager) run.getBean("taskManager");
        taskManager.taskRechargeQuery();
    }

}
