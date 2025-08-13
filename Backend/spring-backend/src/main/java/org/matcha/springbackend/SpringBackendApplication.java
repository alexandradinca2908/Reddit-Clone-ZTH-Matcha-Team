package org.matcha.springbackend;

import org.matcha.springbackend.logger.LogManager;
import org.matcha.springbackend.logger.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBackendApplication {
    public static void main(String[] args) {
        //  Logger start
        LogManager.getInstance().start();
        Logger.debug("Logger has started");

        SpringApplication.run(SpringBackendApplication.class, args);

        //  Logger shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Logger.debug("Shutting down");
            LogManager.getInstance().shutdown();
        }));
    }
}