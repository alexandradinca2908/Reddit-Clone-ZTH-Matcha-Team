package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.post.PostDto;
import org.matcha.springbackend.dto.post.requestbody.CreatePostBodyDto;
import org.matcha.springbackend.dto.post.requestbody.UpdatePostBodyDto;
import org.matcha.springbackend.dto.vote.AllVotesDto;
import org.matcha.springbackend.dto.vote.requestbody.PutVoteBodyDto;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.enums.VotableType;
import org.matcha.springbackend.loggerobject.Logger;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.response.MessageResponse;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.service.PostService;
import org.matcha.springbackend.service.VoteService;
import org.matcha.springbackend.session.AccountSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.matcha.springbackend.enums.VoteType.stringToVoteType;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final AccountService accountService;
    private final AccountSession accountSession;
    private final PostService postService;
    private final PostMapper postMapper;
    private final VoteService voteService;
    private final VoteMapper voteMapper;

    public PostController(AccountService accountService, AccountSession accountSession,
                          PostService postService, PostMapper postMapper,
                          VoteService voteService, VoteMapper voteMapper) {
        this.accountService = accountService;
        this.accountSession = accountSession;
        this.postService = postService;
        this.postMapper = postMapper;
        this.voteService = voteService;
        this.voteMapper = voteMapper;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<PostDto>>> getPosts() {
        //  Map Post to PostDTO
        List<PostDto> postDtos = postService.getPosts().stream()
                .map(postMapper::modelToDto)
                .toList();

        DataResponse<List<PostDto>> dataResponse = new DataResponse<>(true, postDtos);
        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<DataResponse<PostDto>> getPostById(@PathVariable String id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s not found", id));
        }

        DataResponse<PostDto> dataResponse = new DataResponse<>(true, postMapper.modelToDto(post));
        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping
    public ResponseEntity<DataResponse<PostDto>> createPost(@RequestBody CreatePostBodyDto postDto) {
        Logger.debug("[PostService] addPost called for post title: " + postDto.title());

        Post post;
        try {
            post = postService.addPost(postDto);
        } catch (ResponseStatusException e) {
            throw e;
        }  catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

        //  Send response
        DataResponse<PostDto> dataResponse = new DataResponse<>(true, postMapper.modelToDto(post));
        return ResponseEntity.ok(dataResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<DataResponse<PostDto>> updatePost(@PathVariable String id,
                                                            @RequestBody UpdatePostBodyDto postDto) {
        Logger.info("[PostController] updatePost called for id: " + id);

        Post post;
        try {
            post = postService.updatePost(id, postDto);
            Logger.info("[PostController] Post updated successfully for id: " + id);
        } catch (Exception e) {
            Logger.error("[PostController] Exception at updatePost for id: " + id + ", message: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

        DataResponse<PostDto> dataResponse = new DataResponse<>(true, postMapper.modelToDto(post));
        return ResponseEntity.ok(dataResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable String id) {
        try {
            postService.deletePost(id);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

        MessageResponse messageResponse = new MessageResponse(true,
                "\"Postarea a fost ștearsă cu succes\"");
        return ResponseEntity.ok(messageResponse);
    }

    // TODO
    @PutMapping("/{id}/vote")
    public ResponseEntity<DataResponse<AllVotesDto>> votePost(@PathVariable String id,
                                                              @RequestBody PutVoteBodyDto putVoteDTO) {

        Account currentAccount = accountSession.getCurrentAccount();
        AccountEntity accountEntity = accountService.getAccountEntityById(currentAccount.getAccountId());
        if (accountEntity == null) {
            throw new IllegalArgumentException("Current account does not exist in DB! id: " + currentAccount.getAccountId());
        }
        Vote currentVote = voteService.getVoteByAccountAndVotable(accountEntity, UUID.fromString(id));

        if (putVoteDTO.voteType().equals("none")) {
            voteService.deleteVoteByID(currentVote.getVoteID());
        } else {
            if (currentVote == null) {
                System.out.println("First time voting");
                currentVote = new Vote(UUID.randomUUID(), UUID.fromString(id), VotableType.POST,
                        stringToVoteType(putVoteDTO.voteType()), currentAccount);
                voteService.addVote(currentVote);
            } else {
                if (putVoteDTO.voteType().equals(currentVote.getVoteType().toString())) {
                    System.out.println("Double click");
                    voteService.deleteVoteByID(currentVote.getVoteID());
                } else {
                    currentVote.setVoteType(stringToVoteType(putVoteDTO.voteType()));
                    System.out.println("Changed your mind");
                    voteService.updateVote(currentVote);
                }
            }
        }

        DataResponse<AllVotesDto> dataResponse = new DataResponse<>(true, voteMapper.modelToDto(currentVote));
        return ResponseEntity.ok(dataResponse);
    }
}