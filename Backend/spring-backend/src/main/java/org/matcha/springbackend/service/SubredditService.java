package org.matcha.springbackend.service;

import org.matcha.springbackend.model.Subreddit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubredditService {
    private final List<Subreddit> subreddits;

    public SubredditService() {
        this.subreddits = new ArrayList<>();
    }

    public Subreddit findByDisplayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return null;
        }

        for (Subreddit subreddit : subreddits) {
            if (displayName.equals(subreddit.getDisplayName())) {
                return subreddit;
            }
        }

        return null;
    }

    public List<Subreddit> getSubreddits() {
        return subreddits;
    }

    public void addSubreddit(Subreddit subreddit) {
        subreddits.add(subreddit);
    }
}
