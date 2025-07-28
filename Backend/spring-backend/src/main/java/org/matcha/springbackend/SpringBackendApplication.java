package org.matcha.springbackend;

import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Subreddit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.OffsetDateTime;
import java.util.UUID;

@SpringBootApplication
public class SpringBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBackendApplication.class, args);

        //  DELETE WHEN DB IS POPULATED
        Account account = new Account();
        Subreddit subreddit = new Subreddit(UUID.randomUUID(), account, "echipa 1", "matchaaa", false,
                1, 0, "", OffsetDateTime.now());
    }
}
