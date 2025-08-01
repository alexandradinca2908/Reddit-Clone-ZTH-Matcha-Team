package org.matcha.springbackend.loggerobjects;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//TODO

public class AsyncFileLogger {
    private static final String LOG_FILE = "application.log";
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    private static volatile boolean running = true;

    static {
        Thread loggerThread = new Thread(() -> {
            try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
                while (running || !logQueue.isEmpty()) {
                    String message = logQueue.poll();
                    if (message != null) {
                        writer.write(message + System.lineSeparator());
                        writer.flush();
                    } else {
                        Thread.sleep(10);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, "AsyncFileLoggerThread");
        loggerThread.setDaemon(true);
        loggerThread.start();
    }

    public static void info(String msg) {
        enqueue("INFO: " + msg);
    }

    public static void warn(String msg) {
        enqueue("WARN: " + msg);
    }

    public static void error(String msg) {
        enqueue("ERROR: " + msg);
    }

    private static void enqueue(String msg) {
        logQueue.offer(msg);
    }

    public static void shutdown() {
        running = false;
    }
}

