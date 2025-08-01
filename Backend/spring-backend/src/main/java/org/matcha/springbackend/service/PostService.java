package org.matcha.springbackend.service;

import org.matcha.springbackend.dto.post.requestbody.CreatePostBodyDto;
import org.matcha.springbackend.dto.post.requestbody.UpdatePostBodyDto;
import org.matcha.springbackend.entities.PostEntity;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Subreddit;
import org.matcha.springbackend.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.matcha.springbackend.loggerobject.Logger;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final AccountService accountService;
    private final SubredditService subredditService;

    public PostService(PostMapper postMapper, PostRepository postRepository, AccountService accountService, SubredditService subredditService) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.accountService = accountService;
        this.subredditService = subredditService;
    }

    public List<Post> getPosts() {
        List<PostEntity> entities = postRepository.findAll();
        return entities.stream()
                .map(postMapper::entityToModel)
                .collect(Collectors.toList());
    }

    public Post addPost(CreatePostBodyDto postDto) {
        Account account = accountService.findByUsername(postDto.author());
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

        Subreddit subreddit = subredditService.findByName(postDto.subreddit());
        if  (subreddit == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subreddit not found");
        }

        OffsetDateTime createdAt = OffsetDateTime.now();

        //  Create and add post
        Post post = new Post(null, postDto.title(), postDto.content(), account, subreddit,
                0, 0, 0, "", false, createdAt, createdAt);
        PostEntity entity = postMapper.modelToEntity(post);

        Logger.debug("[PostService] PostEntity mapped: " + entity);

        if (entity.getAccount() != null) {
            Logger.debug("[PostService] AccountEntity ID: " + entity.getAccount().getAccountId());
        }

        if (entity.getSubreddit() != null) {
            Logger.debug("[PostService] SubredditEntity ID: " + entity.getSubreddit().getSubredditId());
        }

        try {
            postRepository.save(entity);
            Logger.info("[PostService] Post saved with title: " + post.getTitle());
        } catch (Exception e) {
            Logger.error("[PostService] Exception at save: " + e.getMessage());
            throw e;
        }

        //  Retrieve JPA-populated entity as model
        return postMapper.entityToModel(entity);
    }

    public Post updatePost(String id, UpdatePostBodyDto postDto) {
        // Get post by id
        Post post = this.getPostById(id);

        if (post == null) {
            Logger.warn("[PostService] Post not found for id: " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s not found", id));
        }

        Logger.info("[PostService] Updating post with id: " + id);

        post.setTitle(postDto.title());
        post.setContent(postDto.content());

        PostEntity entity = postMapper.modelToEntity(post);

        if (postRepository.existsById(entity.getPostID())) {
            postRepository.save(entity);
        } else {
            throw new IllegalArgumentException("Post with ID " + entity.getPostID() + " does not exist.");
        }

        return postMapper.entityToModel(entity);
    }

    public Post getPostById(String id) {
        return postRepository.findById(java.util.UUID.fromString(id))
                .map(postMapper::entityToModel)
                .orElse(null);
    }

    public void deletePost(String id) {
        Post post = this.getPostById(id);

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s not found", id));
        }

        postRepository.deleteById(java.util.UUID.fromString(id));
    }

    //  TODO
    public boolean votePost(String id, String vote) {
        Post post = getPostById(id);
        if (post == null) {
            return false;
        }
        // Implement vote logic here
        return true;
    }

    public PostEntity getPostEntityById(String id) {
        return postRepository.findByPostID(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Account not found in DB for id: " + id));
    }
}
