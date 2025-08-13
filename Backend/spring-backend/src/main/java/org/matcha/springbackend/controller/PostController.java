package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.comment.CommentDto;
import org.matcha.springbackend.dto.comment.requestbody.AddCommentBodyDTO;
import org.matcha.springbackend.dto.post.PostDto;
import org.matcha.springbackend.dto.post.requestbody.CreatePostBodyDto;
import org.matcha.springbackend.dto.post.requestbody.UpdatePostBodyDto;
import org.matcha.springbackend.dto.vote.AllVotesDto;
import org.matcha.springbackend.dto.vote.requestbody.PutVoteBodyDto;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.logger.Logger;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.repository.PostRepository;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.response.MessageResponse;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.service.CommentService;
import org.matcha.springbackend.service.PostService;
import org.matcha.springbackend.service.VoteService;
import org.matcha.springbackend.session.AccountSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;

    public PostController(AccountService accountService, AccountSession accountSession,
                          PostService postService, PostMapper postMapper,
                          VoteService voteService, CommentService commentService, CommentMapper commentMapper, PostRepository postRepository) {
        this.accountService = accountService;
        this.accountSession = accountSession;
        this.postService = postService;
        this.postMapper = postMapper;
        this.voteService = voteService;
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.postRepository = postRepository;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse<PostDto>> createPostNoImage(@RequestBody CreatePostBodyDto postDto) {
        Logger.debug("[PostService] addPost called for post title: " + postDto.title());

        Post post;
        try {
            post = postService.addPost(postDto);
        } catch (ResponseStatusException e) {
            throw e;
        }  catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

        Logger.debug("New post DTO looks like this:\n" + postMapper.modelToDto(post).toString());

        //  Send response
        DataResponse<PostDto> dataResponse = new DataResponse<>(true, postMapper.modelToDto(post));
        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<DataResponse<PostDto>> createPostWithImage(@RequestBody CreatePostBodyDto postDto) {
        //  TODO: RECEIVE FORM DATA AND SEND TO C#
        return null;
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

    @PutMapping("/{postId}/vote")
    public ResponseEntity<DataResponse<AllVotesDto>> votePost(@PathVariable String postId,
                                                              @RequestBody PutVoteBodyDto putVoteDto) {

        Account currentAccount = accountSession.getCurrentAccount();
        AccountEntity accountEntity = accountService.getAccountEntityById(currentAccount.getAccountId());

        if (accountEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist in DB or was deleted! id: " + currentAccount.getAccountId());
        }

        if (!postRepository.existsByPostIDAndIsDeletedFalse(UUID.fromString(postId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Post does not exist in DB or was deleted! id: " + postId);
        }

        Vote currentVote = voteService.getVoteByAccountAndVotable(accountEntity, UUID.fromString(postId));
        VoteType newVoteType = stringToVoteType(putVoteDto.voteType());
        boolean hasPreviousVote = currentVote != null;

        // "None" or double-click
        if (hasPreviousVote && (VoteType.NONE.equals(newVoteType)
                || currentVote.getVoteType().equals(newVoteType))) {
            voteService.deleteVoteForPost(currentVote.getVoteID());

            Logger.info("[PostController] Vote deleted for account: " + currentAccount.getUsername()
                    + " and post: " + postId);

        // First time voting
        } else if (!hasPreviousVote && !VoteType.NONE.equals(newVoteType)) {
            voteService.addVoteForPost(postId, newVoteType, currentAccount);

            Logger.info("[PostController] Vote added for account: " + currentAccount.getUsername()
                    + " and post: " + postId);

        //  Change vote
        } else if (hasPreviousVote) {
            currentVote.setVoteType(newVoteType);
            voteService.updateVoteForPost(currentVote);

            Logger.info("[PostController] Vote updated for account: " + currentAccount.getUsername()
                    + " and post: " + postId);
        }

        AllVotesDto allVotesDto = voteService.getUpdatedPost(postId, accountEntity);

        DataResponse<AllVotesDto> dataResponse = new DataResponse<>(true, allVotesDto);
        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<DataResponse<List<CommentDto>>> getCommentsFromPost(@PathVariable String postId) {
        Post post = postService.getPostById(postId);
        if (post == null || post.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s not found", postId));
        }
        //  Map Comments to CommentDTOs
        List<CommentDto> commentDtos = commentService.getCommentsByPostId(UUID.fromString(postId))
                .stream()
                .map(commentMapper::modelToDto)
                .toList();

        DataResponse<List<CommentDto>> dataResponse = new DataResponse<>(true, commentDtos);
        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<DataResponse<CommentDto>> addCommentToPost(@PathVariable String postId,
                                                                     @RequestBody AddCommentBodyDTO commentDTO) {
        Post post = postService.getPostById(postId);
        if (post == null || post.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Post with UUID %s not found", postId));
        }
        Comment comment;
        try {
            comment = commentService.addComment(postId, commentDTO);
        } catch (ResponseStatusException e) {
            throw e;
        }  catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

        DataResponse<CommentDto> dataResponse = new DataResponse<>(true, commentMapper.modelToDto(comment));
        return ResponseEntity.ok(dataResponse);
    }
}