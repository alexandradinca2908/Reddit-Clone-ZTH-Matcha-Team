package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.post.PostDTO;
import org.matcha.springbackend.dto.vote.AllVotesDTO;
import org.matcha.springbackend.dto.post.requestbody.CreatePostBodyDTO;
import org.matcha.springbackend.dto.post.requestbody.UpdatePostBodyDTO;
import org.matcha.springbackend.dto.vote.requestbody.PutVoteBodyDTO;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.entities.VotableType;
import org.matcha.springbackend.entities.VoteType;
import org.matcha.springbackend.loggerobjects.Logger;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Subreddit;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.response.MessageResponse;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.service.PostService;
import org.matcha.springbackend.service.SubredditService;
import org.matcha.springbackend.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.matcha.springbackend.entities.VoteType.stringToVoteType;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final PostService postService;
    private final PostMapper postMapper;
    private final SubredditService subredditService;
    private final VoteService voteService;
    private final VoteMapper voteMapper;

    public PostController(AccountService accountService, AccountMapper accountMapper,
                          PostService postService, PostMapper postMapper,
                          SubredditService subredditService,
                          VoteService voteService, VoteMapper voteMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.postService = postService;
        this.postMapper = postMapper;
        this.subredditService = subredditService;
        this.voteService = voteService;
        this.voteMapper = voteMapper;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<PostDTO>>> getPosts() {
        //  Map Post to PostDTO
        List<PostDTO> postDTOs = postService.getPosts().stream()
                .map(postMapper::modelToDTO)
                .toList();

        DataResponse<List<PostDTO>> dataResponse = new DataResponse<>(true, postDTOs);
        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<DataResponse<PostDTO>> getPostById(@PathVariable String id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s not found", id));
        }

        DataResponse<PostDTO> dataResponse = new DataResponse<>(true, postMapper.modelToDTO(post));
        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping
    public ResponseEntity<DataResponse<PostDTO>> createPost(@RequestBody CreatePostBodyDTO postDTO) {
        //  Create post fields
        UUID uuid = UUID.randomUUID();

        Account account = accountService.findByUsername(postDTO.author());
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

        Subreddit subreddit = subredditService.findByName(postDTO.subreddit());
        if  (subreddit == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subreddit not found");
        }

        OffsetDateTime createdAt = OffsetDateTime.now();

        //  Create and add post
        Post post = new Post(uuid, postDTO.title(), postDTO.content(), account, subreddit,
                0, 0, 0, "", false, createdAt, createdAt);

        postService.addPost(post);

        //  Send response
        DataResponse<PostDTO> dataResponse = new DataResponse<>(true, postMapper.modelToDTO(post));
        return ResponseEntity.ok(dataResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<DataResponse<PostDTO>> updatePost(@PathVariable String id,
                                                            @RequestBody UpdatePostBodyDTO postDTO) {
        Logger.info("[PostController] updatePost called for id: " + id);
        try {
            // Get post by id
            Post post = postService.getPostById(id);
            if (post == null) {
                Logger.warn("[PostController] Post not found for id: " + id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Post with UUID %s not found", id));
            }
            Logger.info("[PostController] Updating post with id: " + id);
            post.setTitle(postDTO.title());
            post.setContent(postDTO.content());
            postService.updatePost(post);
            Logger.info("[PostController] Post updated successfully for id: " + id);
            DataResponse<PostDTO> dataResponse = new DataResponse<>(true, postMapper.modelToDTO(post));
            Logger.info("[PostController] PostDTO mapped and response ready for id: " + id);
            return ResponseEntity.ok(dataResponse);
        } catch (Exception e) {
            Logger.error("[PostController] Exception at updatePost for id: " + id + ", message: " + e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {

        Post post = postService.getPostById(id);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s not found", id));
        }
        boolean isDeleted = postService.deletePost(id);
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s could not be deleted", id));
        }
        return ResponseEntity.noContent().build();
    }

    // TODO
    @PutMapping("/{id}/vote")
    public ResponseEntity<DataResponse<AllVotesDTO>> votePost(@PathVariable String id,
                                                              @RequestBody PutVoteBodyDTO putVoteDTO) {

        Account currentAccount = accountService.getCurrentAccount();
        AccountEntity accountEntity = accountService.getAccountEntityById(currentAccount.getAccountId());
        if (accountEntity == null) {
            throw new IllegalArgumentException("Current account does not exist in DB! id: " + currentAccount.getAccountId());
        }
        Vote currentVote = voteService.getVoteByAccountAndVotable(accountEntity, UUID.fromString(id));

        if (putVoteDTO.voteType().equals("none")) {
            voteService.deleteVoteByID(currentVote.getVoteID());

        } else {
            //  Adding a vote
            if (currentVote == null) {
                currentVote = new Vote(UUID.randomUUID(), UUID.fromString(id), VotableType.POST,
                        stringToVoteType(putVoteDTO.voteType()), currentAccount);

                voteService.addVote(currentVote);

            //  Updating a vote
            } else {
                currentVote.setVoteType(stringToVoteType(putVoteDTO.voteType()));
                voteService.updateVote(currentVote);
            }
        }

        DataResponse<AllVotesDTO> dataResponse = new DataResponse<>(true, voteMapper.modelToDTO(currentVote));
        return ResponseEntity.ok(dataResponse);
    }
}