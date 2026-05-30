package com.connectedgames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EdgeServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EdgeServerApplication.class, args);
    }
}