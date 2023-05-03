package com.csaba79coder.loggingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// responsible for logging the whole application
@SpringBootApplication
@EnableDiscoveryClient // added @EnableDiscoveryClient for this to work as a discovery client
public class LoggingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoggingServiceApplication.class, args);
    }
}
