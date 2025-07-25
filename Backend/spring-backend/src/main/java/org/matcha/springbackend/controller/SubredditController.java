package org.matcha.springbackend.controller;

import org.matcha.springbackend.model.Subreddit;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubredditController {
    private final List<Subreddit> subreddits;

    public SubredditController(List<Subreddit> subreddits) {
        this.subreddits = subreddits;
    }
}
