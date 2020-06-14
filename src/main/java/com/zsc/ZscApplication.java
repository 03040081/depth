package com.zsc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:quartz.properties","classpath:ribbon.properties"})
@SpringBootApplication
public class ZscApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZscApplication.class, args);
    }

}
