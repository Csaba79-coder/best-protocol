package com.csaba79coder.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * This is the main class for the User Service application. It is
 * annotated with @SpringBootApplication, which is a convenience
 * annotation that combines three other annotations:
 * - @Configuration: indicates that this class provides configuration
 *   information for the application context.
 * - @EnableAutoConfiguration: enables Spring Boot's autoconfiguration
 *   feature, which automatically configures the application based on
 *   classpath settings, other beans, and various property settings.
 * - @ComponentScan: tells Spring to scan this package and its
 *   sub-packages for components (i.e., beans) that can be autowired
 *   into other beans.
 *
 * Additionally, this class is annotated with @EnableDiscoveryClient,
 * which enables it to register with a service registry (such as Eureka)
 * as a discovery client. This allows other services to discover and
 * communicate with this service.
 */
@SpringBootApplication
@EnableDiscoveryClient // added @EnableDiscoveryClient for this to work as a discovery client
public class UserServiceApplication {

    /**
     * This is the main method for the User Service application. It
     * starts the Spring application context and launches the
     * application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
