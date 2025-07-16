package org.example.services;

import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;

public class VotingService {
    private static VotingService instance;

    public VotingService() {}

    public static VotingService getInstance() {
        if(instance == null) {
            instance = new VotingService();
        }
        return instance;
    }

    public void votePost(User user, Post post, boolean vote) {
        if (vote) {
            if (post.votingUserID.containsKey(user.getUserID())) {
                if (post.votingUserID.get(user.getUserID()).equals(1)) {
                    post.downvote();
                    post.votingUserID.remove(user.getUserID());
                } else {
                    post.upvote();
                    post.upvote();
                    post.votingUserID.put(user.getUserID(), 1);
                }
            } else {
                post.upvote();
                post.votingUserID.put(user.getUserID(), 1);
            }
        } else {
            if (post.votingUserID.containsKey(user.getUserID())) {
                if (post.votingUserID.get(user.getUserID()).equals(-1)) {
                    post.upvote();
                    post.votingUserID.remove(user.getUserID());
                } else {
                    post.downvote();
                    post.downvote();
                    post.votingUserID.put(user.getUserID(), -1);
                }
            } else {
                post.downvote();
                post.votingUserID.put(user.getUserID(), -1);
            }
        }
    }

    public void voteComment(User user, Comment comment, boolean vote) {
        if(vote) {
            if(comment.votingUserID.containsKey(user.getUserID())) {
                if(comment.votingUserID.get(user.getUserID()).equals(1)) {
                    comment.downvote();
                    comment.votingUserID.remove(user.getUserID());
                }
                else {
                    comment.upvote();
                    comment.upvote();
                    comment.votingUserID.put(user.getUserID(), 1);
                }
            }
            else {
                comment.upvote();
                comment.votingUserID.put(user.getUserID(), 1);
            }
        }
        else {
            if(comment.votingUserID.containsKey(user.getUserID())) {
                if(comment.votingUserID.get(user.getUserID()).equals(-1)) {
                    comment.upvote();
                    comment.votingUserID.remove(user.getUserID());
                }
                else {
                    comment.downvote();
                    comment.downvote();
                    comment.votingUserID.put(user.getUserID(), -1);
                }
            }
            else {
                comment.downvote();
                comment.votingUserID.put(user.getUserID(), -1);
            }
        }
    }

    public void voteReply(User user, Comment reply, boolean vote) {
        if(vote) {
            if(reply.votingUserID.containsKey(user.getUserID())) {
                if(reply.votingUserID.get(user.getUserID()).equals(1)) {
                    reply.downvote();
                    reply.votingUserID.remove(user.getUserID());
                }
                else {
                    reply.upvote();
                    reply.upvote();
                    reply.votingUserID.put(user.getUserID(), 1);
                }
            }
            else {
                reply.upvote();
                reply.votingUserID.put(user.getUserID(), 1);
            }
        }
        else {
            if(reply.votingUserID.containsKey(user.getUserID())) {
                if(reply.votingUserID.get(user.getUserID()).equals(-1)) {
                    reply.upvote();
                    reply.votingUserID.remove(user.getUserID());
                }
                else {
                    reply.downvote();
                    reply.downvote();
                    reply.votingUserID.put(user.getUserID(), -1);
                }
            }
            else {
                reply.downvote();
                reply.votingUserID.put(user.getUserID(), -1);
            }
        }
    }
}
