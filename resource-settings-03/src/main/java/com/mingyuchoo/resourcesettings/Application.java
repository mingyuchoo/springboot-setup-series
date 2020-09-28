package com.mingyuchoo.resourcesettings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    @Value("${hello}")
    private static String hello;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
