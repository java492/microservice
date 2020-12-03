package com.dataexa;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Reference
@SpringBootApplication
@EnableFeignClients
public class IndicatorManagerApplication {
    public static void main(String[] args) {
        System.setProperty(ConfigFileApplicationListener.CONFIG_NAME_PROPERTY,"common-redis");
        SpringApplication.run(IndicatorManagerApplication.class, args);
    }
}