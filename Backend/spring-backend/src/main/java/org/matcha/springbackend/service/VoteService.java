package org.matcha.springbackend.service;

import org.matcha.springbackend.dto.vote.AllVotesDto;
import org.matcha.springbackend.dto.vote.requestbody.PutVoteBodyDto;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.enums.VotableType;
import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.repository.CommentRepository;
import org.matcha.springbackend.repository.PostRepository;
import org.matcha.springbackend.repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public VoteService(VoteRepository voteRepository, VoteMapper voteMapper, PostRepository postRepository, CommentRepository commentRepository, PostService postService, CommentService commentService) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.commentService = commentService;
    }

    public Vote getVoteByAccountAndVotable(AccountEntity account, UUID votableId) {
        return voteRepository.findByAccountAndVotableId(account, votableId)
                .map(voteMapper::entityToModel)
                .orElse(null);
    }

    @Transactional
    public void addVoteForPost(String commentId, String newVoteType, Account currentAccount) {
        Vote vote = new Vote(UUID.randomUUID(), UUID.fromString(commentId), VotableType.COMMENT,
                stringToVoteType(newVoteType), currentAccount);

        VoteEntity entity = voteMapper.modelToEntity(vote);
        entity.setVoteId(null);
        voteRepository.save(entity);

        postRepository.findByPostID(vote.getVotableID()).ifPresent(post -> {
            if (VoteType.UP.equals(vote.getVoteType())) {
                post.setUpvotes(post.getUpvotes() + 1);
            } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                post.setDownvotes(post.getDownvotes() + 1);
            }

            postRepository.save(post);
        });
    }

    @Transactional
    public void addVoteForComment(String postId, String newVoteType, Account currentAccount) {
        Vote vote = new Vote(UUID.randomUUID(), UUID.fromString(postId), VotableType.COMMENT,
                stringToVoteType(newVoteType), currentAccount);

        VoteEntity entity = voteMapper.modelToEntity(vote);
        entity.setVoteId(null);
        voteRepository.save(entity);

        commentRepository.findByCommentId(vote.getVotableID()).ifPresent(comment -> {
            if (VoteType.UP.equals(vote.getVoteType())) {
                comment.setUpvotes(comment.getUpvotes() + 1);
            } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                comment.setDownvotes(comment.getDownvotes() + 1);
            }
            commentRepository.save(comment);
        });
    }

    @Transactional
    public void deleteVoteByID(UUID id) {
        Vote vote = voteRepository.findById(id)
                .map(voteMapper::entityToModel)
                .orElseThrow(() -> new IllegalArgumentException("Vote with ID " + id + " does not exist."));

        voteRepository.deleteByVoteId(id);

        //  Post
        if (VotableType.POST.equals(vote.getVotableType())) {
            postRepository.findByPostID(vote.getVotableID()).ifPresent(post -> {
                if (VoteType.UP.equals(vote.getVoteType())) {
                    post.setUpvotes(post.getUpvotes() - 1);
                } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                    post.setDownvotes(post.getDownvotes() - 1);
                }

                postRepository.save(post);
            });

        //  Comment
        } else {
            commentRepository.findByCommentId(vote.getVotableID()).ifPresent(comment -> {
                if (VoteType.UP.equals(vote.getVoteType())) {
                    comment.setUpvotes(comment.getUpvotes() - 1);
                } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                    comment.setDownvotes(comment.getDownvotes() - 1);
                }

                commentRepository.save(comment);
            });
        }
    }

    @Transactional
    public void updateVote(Vote vote) {
        VoteEntity entity = voteRepository.findById(vote.getVoteID())
                .orElseThrow(() -> new IllegalArgumentException("Vote with ID " + vote.getVoteID() + " does not exist."));

        entity.setVoteType(vote.getVoteType());
        voteRepository.save(entity);

        if (VotableType.POST.equals(vote.getVotableType())) {
            postRepository.findByPostID(vote.getVotableID()).ifPresent(post -> {
                if (VoteType.UP.equals(vote.getVoteType())) {
                    post.setUpvotes(post.getUpvotes() + 1);
                    post.setDownvotes(post.getDownvotes() - 1);
                } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                    post.setDownvotes(post.getDownvotes() + 1);
                    post.setUpvotes(post.getUpvotes() - 1);
                }

                postRepository.save(post);
            });
        } else {
            commentRepository.findByCommentId(vote.getVotableID()).ifPresent(comment -> {
                if (VoteType.UP.equals(vote.getVoteType())) {
                    comment.setUpvotes(comment.getUpvotes() + 1);
                    comment.setDownvotes(comment.getDownvotes() - 1);
                } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                    comment.setDownvotes(comment.getDownvotes() + 1);
                    comment.setUpvotes(comment.getUpvotes() - 1);
                }

                commentRepository.save(comment);
            });
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
