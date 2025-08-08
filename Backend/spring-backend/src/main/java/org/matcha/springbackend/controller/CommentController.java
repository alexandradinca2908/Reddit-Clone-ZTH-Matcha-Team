package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.comment.CommentDto;
import org.matcha.springbackend.dto.comment.requestbody.AddCommentBodyDTO;
import org.matcha.springbackend.dto.comment.requestbody.EditCommentBodyDTO;
import org.matcha.springbackend.dto.vote.AllVotesDto;
import org.matcha.springbackend.dto.vote.requestbody.PutVoteBodyDto;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.enums.VotableType;
import org.matcha.springbackend.loggerobject.Logger;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.repository.CommentRepository;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.response.MessageResponse;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.service.CommentService;
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
@RequestMapping("/comments")
public class CommentController {
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final AccountService accountService;
    private final AccountSession accountSession;
    private final VoteService voteService;

    public CommentController(CommentMapper commentMapper, CommentService commentService,
                             AccountService accountService, AccountSession accountSession,
                             VoteService voteService) {
        this.commentMapper = commentMapper;
        this.commentService = commentService;
        this.accountService = accountService;
        this.accountSession = accountSession;
        this.voteService = voteService;
    }

    @PutMapping("/{commentId}/vote")
    public ResponseEntity<DataResponse<AllVotesDto>> voteComment(@PathVariable String commentId,
                                                                 @RequestBody PutVoteBodyDto putVoteDto) {
        Account currentAccount = accountSession.getCurrentAccount();
        AccountEntity accountEntity = accountService.getAccountEntityById(currentAccount.getAccountId());
        if (accountEntity == null) {
            throw new IllegalArgumentException("Current account does not exist in DB! id: " + currentAccount.getAccountId());
        }

        Vote currentVote = voteService.getVoteByAccountAndVotable(accountEntity, UUID.fromString(commentId));
        String newVoteType = putVoteDto.voteType().toLowerCase();
        boolean hasPreviousVote = currentVote != null;

        // Double click or "none"
        if (hasPreviousVote && (newVoteType.equals("none")
                || newVoteType.equals(currentVote.getVoteType().toString().toLowerCase()))) {
            voteService.deleteVoteByID(currentVote.getVoteID());

            Logger.info("[VoteController] Vote deleted for account: " + currentAccount.getUsername() + " and comment: " + commentId);

        // First time voting
        } else if (!hasPreviousVote && !newVoteType.equals("none")) {
            voteService.addVoteForComment(commentId, newVoteType, currentAccount);

            Logger.info("[VoteController] Vote added for account: " + currentAccount.getUsername() + " and comment: " + commentId);

        // Change vote
        } else if (hasPreviousVote) {
            currentVote.setVoteType(stringToVoteType(newVoteType));
            voteService.updateVote(currentVote);

            Logger.info("[VoteController] Vote updated for account: " + currentAccount.getUsername() + " and comment: " + commentId);
        }

        AllVotesDto allVotesDto = voteService.getUpdatedComment(commentId, accountEntity);

        DataResponse<AllVotesDto> dataResponse = new DataResponse<>(true, allVotesDto);
        return ResponseEntity.ok(dataResponse);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<DataResponse<CommentDto>> updateComment(@PathVariable String commentId,
                                                                  @RequestBody EditCommentBodyDTO contentDto) {
        Comment comment;
        try {
            comment = commentService.updateComment(commentId, contentDto.content());
            Logger.info("[CommentController] Comment updated successfully for id: " + commentId);
        } catch (Exception e) {
            Logger.error("[CommentController] Exception at updateComment for id: " + commentId + ", message: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

        DataResponse<CommentDto> dataResponse = new DataResponse<>(true, commentMapper.modelToDto(comment));
        return ResponseEntity.ok(dataResponse);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(commentId);

        MessageResponse messageResponse = new MessageResponse(true,
                "Comentariul a fost sters cu succes");
        return ResponseEntity.ok(messageResponse);
    }
}
