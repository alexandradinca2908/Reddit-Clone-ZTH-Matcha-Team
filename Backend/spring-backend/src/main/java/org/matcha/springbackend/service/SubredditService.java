package org.matcha.springbackend.service;

import org.matcha.springbackend.entities.SubredditEntity;
import org.matcha.springbackend.model.Subreddit;
import org.matcha.springbackend.repositories.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubredditService {
    private final SubredditRepository subredditRepository;

    @Autowired
    public SubredditService(SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }

    public Subreddit findByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        Optional<SubredditEntity> entityOpt = subredditRepository.findByName(name);
        if (entityOpt.isPresent()) {
            SubredditEntity entity = entityOpt.get();
            Subreddit subreddit = new Subreddit();
            subreddit.setId(entity.getSubredditId());
            subreddit.setName(entity.getName());
            subreddit.setDescription(entity.getDescription());
            subreddit.setDeleted(entity.isDeleted());
            subreddit.setCreatedAt(entity.getCreatedAt());
            return subreddit;
        }
        return null;
    }

    public List<Subreddit> getSubreddits() {
        List<Subreddit> result = new ArrayList<>();
        List<SubredditEntity> entities = subredditRepository.findAll();
        for (SubredditEntity entity : entities) {
            Subreddit subreddit = new Subreddit();
            subreddit.setId(entity.getSubredditId());
            subreddit.setName(entity.getName());
            subreddit.setDescription(entity.getDescription());
            subreddit.setDeleted(entity.isDeleted());
            subreddit.setCreatedAt(entity.getCreatedAt());
            result.add(subreddit);
        }
        return result;
    }

    public void addSubreddit(Subreddit subreddit) {
        if (subreddit == null) return;
        SubredditEntity entity = new SubredditEntity();
        entity.setName(subreddit.getName());
        entity.setDescription(subreddit.getDescription());
        entity.setDeleted(subreddit.isDeleted());
        entity.setCreatedAt(subreddit.getCreatedAt());
        subredditRepository.save(entity);
    }
}
