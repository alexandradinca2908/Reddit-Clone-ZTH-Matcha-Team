package org.matcha.springbackend.service;

import org.matcha.springbackend.dto.vote.AllVotesDto;
import org.matcha.springbackend.dto.vote.requestbody.PutVoteBodyDto;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.enums.VotableType;
import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.logger.Logger;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.repository.CommentRepository;
import org.matcha.springbackend.repository.PostRepository;
import org.matcha.springbackend.repository.VoteRepository;
import org.matcha.springbackend.session.AccountSession;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.matcha.springbackend.enums.VoteType.stringToVoteType;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final AccountSession accountSession;
    private final AccountService accountService;

    public VoteService(VoteRepository voteRepository, VoteMapper voteMapper, PostRepository postRepository, CommentRepository commentRepository, PostService postService, CommentService commentService, AccountSession accountSession, AccountService accountService) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.commentService = commentService;
        this.accountSession = accountSession;
        this.accountService = accountService;
    }

    public Vote getVoteByAccountAndVotable(AccountEntity account, UUID votableId) {
        return voteRepository.findByAccountAndVotableId(account, votableId)
                .map(voteMapper::entityToModel)
                .orElse(null);
    }

    //  POST VOTING
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "posts", allEntries = true),
            @CacheEvict(value = "userVotes", key = "#currentAccount.accountId")
    })
    public void votePost(String postId, PutVoteBodyDto putVoteDto,
                         Account currentAccount, AccountEntity accountEntity) {

        if (!postRepository.existsByPostIDAndIsDeletedFalse(UUID.fromString(postId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Post does not exist in DB or was deleted! id: " + postId);
        }

        Vote currentVote = getVoteByAccountAndVotable(accountEntity, UUID.fromString(postId));
        VoteType newVoteType = stringToVoteType(putVoteDto.voteType());
        boolean hasPreviousVote = currentVote != null;

        // "None" or double-click
        if (hasPreviousVote && (VoteType.NONE.equals(newVoteType)
                || currentVote.getVoteType().equals(newVoteType))) {
            deleteVoteForPost(currentVote.getVoteID());

            Logger.info("[PostController] Vote deleted for account: " + currentAccount.getUsername()
                    + " and post: " + postId);

            // First time voting
        } else if (!hasPreviousVote && !VoteType.NONE.equals(newVoteType)) {
            addVoteForPost(postId, newVoteType, currentAccount);

            Logger.info("[PostController] Vote added for account: " + currentAccount.getUsername()
                    + " and post: " + postId);

            //  Change vote
        } else if (hasPreviousVote) {
            currentVote.setVoteType(newVoteType);
            updateVoteForPost(currentVote);

            Logger.info("[PostController] Vote updated for account: " + currentAccount.getUsername()
                    + " and post: " + postId);
        }
    }

    @Transactional
    public void addVoteForPost(String postId, VoteType newVoteType, Account currentAccount) {
        UUID postUUID = UUID.fromString(postId);

        Vote vote = new Vote(null, postUUID, VotableType.POST,
                newVoteType, currentAccount);

        VoteEntity entity = voteMapper.modelToEntity(vote);
        voteRepository.save(entity);

        postRepository.findByPostIDAndIsDeletedFalse(vote.getVotableID()).ifPresent(post -> {
            if (VoteType.UP.equals(newVoteType)) {
                postRepository.incrementUpvotes(postUUID);
            } else if (VoteType.DOWN.equals(newVoteType)) {
                postRepository.incrementDownvotes(postUUID);
            }
        });
    }

    @Transactional
    public void deleteVoteForPost(UUID id) {
        Vote vote = voteRepository.findById(id)
                .map(voteMapper::entityToModel)
                .orElseThrow(() -> new IllegalArgumentException("Vote with ID " + id + " does not exist."));

        voteRepository.deleteByVoteId(id);

        if (VoteType.UP.equals(vote.getVoteType())) {
            postRepository.decrementUpvotes(vote.getVotableID());
        } else if (VoteType.DOWN.equals(vote.getVoteType())) {
            postRepository.decrementDownvotes(vote.getVotableID());
        }
    }

    @Transactional
    public void updateVoteForPost(Vote vote) {
        VoteEntity entity = voteRepository.findById(vote.getVoteID())
                .orElseThrow(() -> new IllegalArgumentException("Vote with ID " + vote.getVoteID() + " does not exist."));

        VoteType oldVoteType = entity.getVoteType();
        VoteType newVoteType = vote.getVoteType();
        UUID postId = vote.getVotableID();

        // Update vote
        entity.setVoteType(newVoteType);
        voteRepository.save(entity);

        // Atomic update of vote counts
        if (oldVoteType == VoteType.UP && newVoteType == VoteType.DOWN) {
            postRepository.decrementUpvotesAndIncrementDownvotes(postId);
        } else if (oldVoteType == VoteType.DOWN && newVoteType == VoteType.UP) {
            postRepository.decrementDownvotesAndIncrementUpvotes(postId);
        } else if (oldVoteType ==  VoteType.UP && newVoteType == VoteType.UP) {
            postRepository.decrementUpvotes(postId);
        } else if (oldVoteType == VoteType.DOWN && newVoteType == VoteType.DOWN) {
            postRepository.decrementDownvotes(postId);
        }
    }

    //  COMMENT VOTING
    @Transactional
    public void voteComment(String commentId, PutVoteBodyDto putVoteDto,
                            Account currentAccount, AccountEntity accountEntity) {
        Comment comment = commentService.getCommentById(commentId);

        if (comment == null || comment.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Comment with UUID %s not found", commentId));
        }

        Vote currentVote = getVoteByAccountAndVotable(accountEntity, UUID.fromString(commentId));
        VoteType newVoteType = stringToVoteType(putVoteDto.voteType());
        boolean hasPreviousVote = currentVote != null;

        // Double click or "none"
        if (hasPreviousVote && (VoteType.NONE.equals(newVoteType)
                || currentVote.getVoteType().equals(newVoteType))) {
            deleteVoteForComment(currentVote.getVoteID());

            Logger.info("[CommentController] Vote deleted for account: " + currentAccount.getUsername() + " and comment: " + commentId);

            // First time voting
        } else if (!hasPreviousVote && !VoteType.NONE.equals(newVoteType)) {
            addVoteForComment(commentId, newVoteType, currentAccount);

            Logger.info("[CommentController] Vote added for account: " + currentAccount.getUsername() + " and comment: " + commentId);

            // Change vote
        } else if (hasPreviousVote) {
            currentVote.setVoteType(newVoteType);
            updateVoteForComment(currentVote);

            Logger.info("[CommentController] Vote updated for account: " + currentAccount.getUsername() + " and comment: " + commentId);
        }
    }

    @Transactional
    public void addVoteForComment(String commentId, VoteType newVoteType, Account currentAccount) {
        UUID commentUUID = UUID.fromString(commentId);

        Vote vote = new Vote(null, commentUUID, VotableType.COMMENT,
                newVoteType, currentAccount);

        VoteEntity entity = voteMapper.modelToEntity(vote);
        voteRepository.save(entity);

        commentRepository.findByCommentId(vote.getVotableID()).ifPresent(comment -> {
            if (VoteType.UP.equals(newVoteType)) {
                commentRepository.incrementUpvotes(commentUUID);
            } else if (VoteType.DOWN.equals(newVoteType)) {
                commentRepository.incrementDownvotes(commentUUID);
            }
        });
    }

    @Transactional
    public void deleteVoteForComment(UUID id) {
        Vote vote = voteRepository.findById(id)
                .map(voteMapper::entityToModel)
                .orElseThrow(() -> new IllegalArgumentException("Vote with ID " + id + " does not exist."));

        voteRepository.deleteByVoteId(id);

        if (VoteType.UP.equals(vote.getVoteType())) {
            commentRepository.decrementUpvotes(vote.getVotableID());
        } else if (VoteType.DOWN.equals(vote.getVoteType())) {
            commentRepository.decrementDownvotes(vote.getVotableID());
        }
    }

    @Transactional
    public void updateVoteForComment(Vote vote) {
        VoteEntity entity = voteRepository.findById(vote.getVoteID())
                .orElseThrow(() -> new IllegalArgumentException("Vote with ID " + vote.getVoteID() + " does not exist."));

        VoteType oldVoteType = entity.getVoteType();
        VoteType newVoteType = vote.getVoteType();
        UUID commentId = vote.getVotableID();

        entity.setVoteType(newVoteType);
        voteRepository.save(entity);

        if (oldVoteType == VoteType.UP && newVoteType == VoteType.DOWN) {
            commentRepository.decrementUpvotesAndIncrementDownvotes(commentId);
        } else if (oldVoteType == VoteType.DOWN && newVoteType == VoteType.UP) {
            commentRepository.decrementDownvotesAndIncrementUpvotes(commentId);
        } else if (oldVoteType ==  VoteType.UP && newVoteType == VoteType.UP) {
            commentRepository.decrementUpvotes(commentId);
        } else if (oldVoteType == VoteType.DOWN && newVoteType == VoteType.DOWN) {
            commentRepository.decrementDownvotes(commentId);
        }
    }

    public AllVotesDto getUpdatedPost(String postId, AccountEntity accountEntity) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + postId);
        }

        Vote newVoteState = getVoteByAccountAndVotable(accountEntity, UUID.fromString(postId));
        String userVote = "none";
        if (newVoteState != null) {
            userVote = newVoteState.getVoteType().toString().toLowerCase();
        }

        return new AllVotesDto(post.getUpvotes(), post.getDownvotes(),
                post.getScore(), userVote);
    }

    public AllVotesDto getUpdatedComment(String commentId, AccountEntity accountEntity) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with id: " + commentId);
        }

        Vote finalVote = getVoteByAccountAndVotable(accountEntity, UUID.fromString(commentId));
        String userVoteString = (finalVote != null) ? finalVote.getVoteType().toString().toLowerCase() : "none";

        return new AllVotesDto(comment.getUpvotes(), comment.getDownvotes(),
                comment.getScore(), userVoteString);
    }
}
