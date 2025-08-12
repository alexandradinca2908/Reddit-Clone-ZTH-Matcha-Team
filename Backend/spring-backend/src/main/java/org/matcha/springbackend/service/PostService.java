package org.matcha.springbackend.service;

import jakarta.transaction.Transactional;
import org.matcha.springbackend.dto.post.requestbody.CreatePostBodyDto;
import org.matcha.springbackend.dto.post.requestbody.UpdatePostBodyDto;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.entities.PostEntity;
import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Subreddit;
import org.matcha.springbackend.repository.PostRepository;
import org.matcha.springbackend.repository.VoteRepository;
import org.matcha.springbackend.session.AccountSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.matcha.springbackend.loggerobject.Logger;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final AccountService accountService;
    private final SubredditService subredditService;
    private final VoteRepository voteRepository;
    private final AccountSession accountSession;

    public PostService(PostMapper postMapper, PostRepository postRepository, AccountService accountService, SubredditService subredditService, VoteRepository voteRepository, AccountSession accountSession) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.accountService = accountService;
        this.subredditService = subredditService;
        this.voteRepository = voteRepository;
        this.accountSession = accountSession;
    }

    public List<Post> getPosts() {
        //  Get all posts from DB
        List<PostEntity> entities = postRepository.findAllByIsDeletedFalseOrderByCreatedAtDesc();

        //  Take all post IDs
        List<UUID> postIds = entities.stream()
                .map(PostEntity::getPostID)
                .collect(Collectors.toList());

        //  Get all votes of current account
        Account currentAccount = accountSession.getCurrentAccount();
        AccountEntity accountEntity = accountService.getAccountEntityById(currentAccount.getAccountId());
        List<VoteEntity> userVotes = voteRepository.findByAccountAndVotableIdIn(
                accountEntity, postIds).orElse(new ArrayList<>());

        //  Create a vote map with the votes for O(1) vote lookup
        Map<UUID, VoteType> voteMap = userVotes.stream()
                .collect(Collectors.toMap(
                        VoteEntity::getVotableId,
                        VoteEntity::getVoteType
                ));

        //  Convert entities to models using the vote map
        return entities.stream()
                .map(entity -> postMapper.entityToModelWithVoteMap(entity, voteMap))
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
                0, 0, 0, 0,  null, "", false,
                createdAt, createdAt, new ArrayList<>());
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

    @Transactional
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
        post.setContent("[edit] " + postDto.content());

        PostEntity entity = postMapper.modelToEntity(post);

        if (postRepository.existsById(entity.getPostID())) {
            postRepository.save(entity);
        } else {
            throw new IllegalArgumentException("Post with ID " + entity.getPostID() + " does not exist.");
        }

        return postMapper.entityToModel(entity);
    }

    public Post getPostById(String id) {
        return postRepository.findByPostIDAndIsDeletedFalse(UUID.fromString(id))
                .map(postMapper::entityToModel)
                .orElse(null);
    }

    @Transactional
    public void deletePost(String id) {
        PostEntity postEntity = postRepository.findByPostIDAndIsDeletedFalse(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with id: " + id));
        //TODO: Account check logic goes here
        postEntity.setDeleted(true);

        postRepository.save(postEntity);
    }

    public PostEntity getPostEntityById(String id) {
        return postRepository.findByPostIDAndIsDeletedFalse(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Post not found in DB for id: " + id));
    }

    public List<Post> getPostsBySubredditName(String subredditName) {
        List<PostEntity> entities = postRepository.findAllBySubreddit_Name(subredditName);
        return entities.stream()
                .map(postMapper::entityToModel)
                .collect(Collectors.toList());
    }
}
