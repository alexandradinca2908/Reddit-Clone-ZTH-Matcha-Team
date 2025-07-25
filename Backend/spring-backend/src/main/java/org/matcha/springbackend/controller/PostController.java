package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.post.PostDTO;
import org.matcha.springbackend.dto.post.requestbody.PostRequestBodyDTO;
import org.matcha.springbackend.dto.post.requestbody.PutRequestBodyDTO;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Subreddit;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.response.MessageResponse;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.service.PostService;
import org.matcha.springbackend.service.SubredditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final AccountService accountService;
    private final PostService postService;
    private final PostMapper postMapper;
    private final SubredditService subredditService;

    public PostController(AccountService accountService, PostService postService, PostMapper postMapper, SubredditService subredditService) {
        this.accountService = accountService;
        this.postService = postService;
        this.postMapper = postMapper;
        this.subredditService = subredditService;
    }

    @GetMapping("")
    public ResponseEntity<DataResponse<List<PostDTO>>> getPosts() {
        //  TODO - retrieve from DB in post array (located in postService)
        //  TODO - just delete this if not necessary

        //  Map Post to PostDTO
        List<PostDTO> postDTOs = postService.getPosts().stream()
                .map(postMapper::modelToDTO)
                .toList();

        DataResponse<List<PostDTO>> dataResponse = new DataResponse<>(true, postDTOs);
        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<DataResponse<PostDTO>> getPostById(@PathVariable String id) {
        //  TODO - retrieve from DB in post array (located in postService)
        //  TODO - just delete this if not necessary

        //  Filter posts by id and map to DTO
        Post post = postService.getPostById(id);

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s not found", id));
        }

        DataResponse<PostDTO> dataResponse = new DataResponse<>(true, postMapper.modelToDTO(post));
        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping
    public ResponseEntity<DataResponse<PostDTO>> createPost(@RequestBody PostRequestBodyDTO postDTO) {
        //  Create post fields
        UUID uuid = UUID.randomUUID();

        Account account = accountService.findByUsername(postDTO.author());
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

        Subreddit subreddit = subredditService.findByDisplayName(postDTO.subreddit());
        if  (subreddit == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subreddit not found");
        }

        OffsetDateTime createdAt = OffsetDateTime.now();

        //  Create and add post
        Post post = new Post(uuid, account, subreddit,
                postDTO.title(), postDTO.content(), "",
                false, createdAt, createdAt);

        postService.addPost(post);

        //  TODO - UPDATE IN DB

        //  Send response
        DataResponse<PostDTO> dataResponse = new DataResponse<>(true, postMapper.modelToDTO(post));
        return ResponseEntity.ok(dataResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<DataResponse<PostDTO>> updatePost(@PathVariable String id,
                                                            @RequestBody PutRequestBodyDTO postDTO) {
        //  Get post by id and set new fields
        Post post = postService.getPostById(id);

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s not found", id));
        }

        post.setTitle(postDTO.title());
        post.setContent(postDTO.content());

        //  TODO - UPDATE IN DB

        DataResponse<PostDTO> dataResponse = new DataResponse<>(true, postMapper.modelToDTO(post));
        return ResponseEntity.ok(dataResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable String id) {
        //  Get post by id and delete it
        boolean isDeleted = postService.deletePost(id);

        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s could not be deleted", id));
        }

        //  TODO - DELETE FROM DB
        //  TODO - you'll probably need to getPost from postService, map to entity and then delete from DB
        //  TODO - if ID is enough and no mapping is needed, then that works too

        MessageResponse messageResponse = new MessageResponse(true,
                "Postarea a fost stearsa cu succes");
        return ResponseEntity.ok(messageResponse);
    }
}