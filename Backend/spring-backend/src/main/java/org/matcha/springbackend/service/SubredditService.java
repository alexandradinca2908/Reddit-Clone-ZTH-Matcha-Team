package org.matcha.springbackend.service;

import org.matcha.springbackend.dto.subreddit.requestbody.CreateSubredditBodyDto;
import org.matcha.springbackend.dto.subreddit.requestbody.UpdateSubredditBodyDto;
import org.matcha.springbackend.entities.PostEntity;
import org.matcha.springbackend.entities.SubredditEntity;
import org.matcha.springbackend.loggerobject.Logger;
import org.matcha.springbackend.mapper.SubredditMapper;
import org.matcha.springbackend.model.Subreddit;
import org.matcha.springbackend.repository.PostRepository;
import org.matcha.springbackend.repository.SubredditRepository;
import org.matcha.springbackend.session.AccountSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final PostRepository postRepository;
    private final AccountSession accountSession;

    public SubredditService(SubredditRepository subredditRepository, SubredditMapper subredditMapper,
                            PostRepository postRepository, AccountSession accountSession) {
        this.subredditRepository = subredditRepository;
        this.subredditMapper = subredditMapper;
        this.postRepository = postRepository;
        this.accountSession = accountSession;
    }

    public Subreddit findByName(String name) {
        // TODO: Always check input for contracts (public methods). If bad input found: must throw error
        if (name == null || name.isEmpty()) {
            return null;
        }

        Optional<SubredditEntity> entityOpt = subredditRepository.findByName(name);

        return entityOpt.map(subredditMapper::entityToModel).orElse(null);
    }

    public List<Subreddit> getSubreddits() {
        List<Subreddit> result = new ArrayList<>();
        List<SubredditEntity> entities = subredditRepository.findAll();

        for (SubredditEntity entity : entities) {
            Subreddit subreddit = subredditMapper.entityToModel(entity);
            result.add(subreddit);
        }

        return result;
    }

    public Subreddit addSubreddit(CreateSubredditBodyDto subredditDTO) {
        Subreddit subreddit = new Subreddit(null, subredditDTO.name(), subredditDTO.displayName(),
                accountSession.getCurrentAccount(), subredditDTO.description(), false,
                0, 0, subredditDTO.iconUrl(), null);

        SubredditEntity entity = subredditMapper.modelToEntity(subreddit);
        subredditRepository.save(entity);

        return subredditMapper.entityToModel(entity);
    }

    public Subreddit getSubredditByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        Optional<SubredditEntity> entityOpt = subredditRepository.findByName(name);

        return entityOpt.map(subredditMapper::entityToModel).orElse(null);
    }

    public Subreddit updateSubreddit(String name, UpdateSubredditBodyDto updateDto) {
        Subreddit subreddit = this.getSubredditByName(name);

        if (subreddit == null) {
            Logger.warn("[SubredditService] Subreddit not found for name: " + name);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Subreddit with name %s not found", name));
        }

        Logger.info("[SubredditService] Updating subreddit with name: " + name);

        subreddit.setDisplayName(updateDto.displayName());
        subreddit.setDescription(updateDto.description());
        subreddit.setIconUrl(updateDto.iconUrl());

        SubredditEntity entity = subredditMapper.modelToEntity(subreddit);

        if (subredditRepository.existsById(entity.getSubredditId())) {
            subredditRepository.save(entity);
        } else {
            throw new IllegalArgumentException("Subreddit with ID " + entity.getSubredditId() + " does not exist.");
        }

        return subredditMapper.entityToModel(entity);
    }

    public void deleteSubredditIfNoPosts(String name) {
        Optional<SubredditEntity> entityOpt = subredditRepository.findByName(name);
        if (entityOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subreddit not found");
            // TODO: When throwing exception, must include:
            // 1. A message that describes (we have this, nice!)
            // 2. The input (minimalistic) that caused this (we don't have this)
            // 3. (optional) The original cause (N/A)
        }

        SubredditEntity entity = entityOpt.get();
        List<PostEntity> posts = postRepository.findAllBySubreddit_Name(name); // TODO: consistency _

        if (!posts.isEmpty()) {
            // TODO: no web stuff in Service. Service does not know we are a web server
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Subreddit cannot be deleted because it has posts");
        }

        subredditRepository.delete(entity);
    }
}
