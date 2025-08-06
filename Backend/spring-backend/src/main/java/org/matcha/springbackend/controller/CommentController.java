package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.comment.CommentDto;
import org.matcha.springbackend.dto.comment.requestbody.AddCommentBodyDTO;
import org.matcha.springbackend.dto.vote.AllVotesDto;
import org.matcha.springbackend.dto.vote.requestbody.PutVoteBodyDto;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.enums.VotableType;
import org.matcha.springbackend.loggerobject.Logger;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.repository.CommentRepository;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.service.CommentService;
import org.matcha.springbackend.service.VoteService;
import org.matcha.springbackend.session.AccountSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.matcha.springbackend.enums.VoteType.stringToVoteType;

@RestController
public class CommentController {
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final AccountService accountService;
    private final AccountSession accountSession;
    private final VoteService voteService;
    private final VoteMapper voteMapper;
    private final CommentRepository commentRepository;

    public CommentController(CommentMapper commentMapper, CommentService commentService,
                             AccountService accountService, AccountSession accountSession,
                             VoteService voteService, VoteMapper voteMapper, CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.commentService = commentService;
        this.accountService = accountService;
        this.accountSession = accountSession;
        this.voteService = voteService;
        this.voteMapper = voteMapper;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<DataResponse<List<CommentDto>>> getCommentsFromPost(@PathVariable String postId) {
        //  Map Comments to CommentDTOs
        List<CommentDto> commentDtos = commentService.getCommentsByPostId(UUID.fromString(postId))
                .stream()
                .map(commentMapper::modelToDto)
                .toList();

        DataResponse<List<CommentDto>> dataResponse = new DataResponse<>(true, commentDtos);
        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<DataResponse<CommentDto>> addCommentToPost(@PathVariable String postId,
                                                                     @RequestBody AddCommentBodyDTO commentDTO) {
        Comment comment;
        try {
            comment = commentService.addCommentToPost(postId, commentDTO);
        } catch (ResponseStatusException e) {
            throw e;
        }  catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

        DataResponse<CommentDto> dataResponse = new DataResponse<>(true, commentMapper.modelToDto(comment));
        return ResponseEntity.ok(dataResponse);
    }

    @PutMapping("/comments/{commentId}/vote")
    public ResponseEntity<DataResponse<AllVotesDto>> voteComment(@PathVariable String commentId,
                                                                 @RequestBody PutVoteBodyDto putVoteDto) {
        Account currentAccount = accountSession.getCurrentAccount();
        AccountEntity accountEntity = accountService.getAccountEntityById(currentAccount.getAccountId());
        if (accountEntity == null) {
            throw new IllegalArgumentException("Current account does not exist in DB! id: " + currentAccount.getAccountId());
        }
        Vote currentVote = voteService.getVoteByAccountAndVotable(accountEntity, UUID.fromString(commentId));

        if (putVoteDto.voteType().equals("none")) {
            voteService.deleteVoteByID(currentVote.getVoteID());
        } else {
            if (currentVote == null) {
                currentVote = new Vote(UUID.randomUUID(), UUID.fromString(commentId), VotableType.COMMENT,
                        stringToVoteType(putVoteDto.voteType()), currentAccount);
                Logger.info("[VoteController] Vote added for account: " + currentAccount.getUsername() + " and comment: " + commentId);
                voteService.addVote(currentVote);
            } else {
                if (putVoteDto.voteType().equals(currentVote.getVoteType().toString().toLowerCase())) {
                    Logger.info("[VoteController] Vote already exists for account: " + currentAccount.getUsername() + " and comment: " + commentId);
                    voteService.deleteVoteByID(currentVote.getVoteID());
                } else {
                    currentVote.setVoteType(stringToVoteType(putVoteDto.voteType()));
                    Logger.info("[VoteController] Vote updated for account: " + currentAccount.getUsername() + " and comment: " + commentId);
                    voteService.updateVote(currentVote);
                }
            }
        }

        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with id: " + commentId);
        }


        Vote finalVote = voteService.getVoteByAccountAndVotable(accountEntity, UUID.fromString(commentId));
        String userVoteString = (finalVote != null) ? finalVote.getVoteType().toString().toLowerCase() : "none";

        AllVotesDto allVotesDto = new AllVotesDto(
                comment.getUpvotes(),
                comment.getDownvotes(),
                comment.getUpvotes() - comment.getDownvotes(),
                userVoteString
        );

        DataResponse<AllVotesDto> dataResponse = new DataResponse<>(true, allVotesDto);
        return ResponseEntity.ok(dataResponse);

    }

}
