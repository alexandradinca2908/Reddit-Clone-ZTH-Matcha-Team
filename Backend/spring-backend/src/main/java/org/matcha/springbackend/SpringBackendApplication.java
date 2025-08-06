package org.matcha.springbackend;

import org.matcha.springbackend.loggerobject.LogManager;
import org.matcha.springbackend.loggerobject.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBackendApplication {
    public static void main(String[] args) {
        LogManager.getInstance().initLoggers();
        SpringApplication.run(SpringBackendApplication.class, args);

        // Example usage
        Logger.info("SpringBackendApplication started.");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LogManager.getInstance().shutdown();
        }));
    }
}