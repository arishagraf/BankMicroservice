package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootApplication annotation enables auto-configuration and components scanning.
 * We run the application and locate to the localhost:8080/
 */
@SpringBootApplication
public class SpringBootH2Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootH2Application.class, args);
    }

}