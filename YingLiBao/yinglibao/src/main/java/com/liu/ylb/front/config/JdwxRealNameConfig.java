package com.liu.ylb.front.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jdwx.realname")
public class JdwxRealNameConfig {
    private String url;
    private String appkey;
}
