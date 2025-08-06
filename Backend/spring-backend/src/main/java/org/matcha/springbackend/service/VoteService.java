package org.matcha.springbackend.service;

import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.enums.VotableType;
import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.repository.CommentRepository;
import org.matcha.springbackend.repository.PostRepository;
import org.matcha.springbackend.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public VoteService(VoteRepository voteRepository, VoteMapper voteMapper, PostRepository postRepository, CommentRepository commentRepository) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Vote getVoteByAccountAndVotable(AccountEntity account, UUID votableId) {
        return voteRepository.findByAccountAndVotableId(account, votableId)
                .map(voteMapper::entityToModel)
                .orElse(null);
    }

    @Transactional
    public void addVote(Vote vote) {
        VoteEntity entity = voteMapper.modelToEntity(vote);
        entity.setVoteId(null);
        voteRepository.save(entity);

        //  Post
        if (VotableType.POST.equals(vote.getVotableType())) {
            postRepository.findByPostID(vote.getVotableID()).ifPresent(post -> {
                if (VoteType.UP.equals(vote.getVoteType())) {
                    if (post.getUpvotes() != null) {
                        post.setUpvotes(post.getUpvotes() + 1);
                    } else {
                        post.setUpvotes(1);
                    }
                    post.setScore(post.getScore() + 1);
                } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                    if (post.getDownvotes() != null) {
                        post.setDownvotes(post.getUpvotes() + 1);
                    } else {
                        post.setDownvotes(1);
                    }
                    post.setScore(post.getScore() - 1);
                }

                postRepository.save(post);
            });

            //  Comment
        } else {
            commentRepository.findByCommentId(vote.getVotableID()).ifPresent(comment -> {
                if (VoteType.UP.equals(vote.getVoteType())) {
                    comment.setUpvotes(comment.getUpvotes() + 1);
                } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                    comment.setDownvotes(comment.getDownvotes() + 1);
                }

                commentRepository.save(comment);
            });
        }
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
                    post.setScore(post.getScore() - 1);
                } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                    post.setDownvotes(post.getDownvotes() - 1);
                    post.setScore(post.getScore() + 1);
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
                    post.setScore(post.getScore() + 1);
                } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                    post.setDownvotes(post.getDownvotes() + 1);
                    post.setUpvotes(post.getUpvotes() - 1);
                    post.setScore(post.getScore() - 1);
                }

                postRepository.save(post);
            });
        } else {
            commentRepository.findByCommentId(vote.getVotableID()).ifPresent(comment -> {
                if (VoteType.UP.equals(vote.getVoteType())) {
                    comment.setUpvotes(comment.getUpvotes() + 1);
                } else if (VoteType.DOWN.equals(vote.getVoteType())) {
                    comment.setDownvotes(comment.getDownvotes() + 1);
                }

                commentRepository.save(comment);
            });
        }
    }
}
