package com.csaba79coder.bestprotocol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// added @EnableDiscoveryClient for this to work as a discovery client
@SpringBootApplication
@EnableDiscoveryClient
public class BestProtocolApplication {

    public static void main(String[] args) {
        SpringApplication.run(BestProtocolApplication.class, args);
    }

}
