package org.matcha.springbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBackendApplication {
    public static void main(String[] args) {
        org.matcha.springbackend.loggerobjects.LogManager.getInstance().initLoggers();
        SpringApplication.run(SpringBackendApplication.class, args);
        // Example usage
        org.matcha.springbackend.loggerobjects.Logger.info("SpringBackendApplication started.");
    }
}