package com.dataexa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author 胡志成
 * @Date 2020/6/2
 */
@SpringBootApplication
@EnableFeignClients
public class UserManagerApplication {
    public static void main(String[] args) {
        System.setProperty(ConfigFileApplicationListener.CONFIG_NAME_PROPERTY,"common-redis");
        SpringApplication.run(UserManagerApplication.class, args);
    }
}
