package org.example.services;

import org.example.dbconnection.DatabaseConnection;
import org.example.loggerobjects.Logger;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.repositories.VoteRepo;

import java.sql.SQLException;

public class VotingService {
    private static VotingService instance;
//    private static final VoteRepo voteRepo = VoteRepo.getInstance();

    public VotingService() {}

    public static VotingService getInstance() {
        if(instance == null) {
            instance = new VotingService();

//            try {
//                voteRepo.load();
//            } catch (SQLException e) {
//                Logger.error("Could not load votes from the database: " + e.getMessage());
//                DatabaseConnection.cannotConnect();
//            }
        }
        return instance;
    }

    public void votePost(User user, Post post, boolean vote) {
        if (vote) { // Upvote
            if (post.getVotingUserID().containsKey(user.getUsername())) { // the user has already voted
                if (post.getVotingUserID().get(user.getUsername()).equals(1)) { // cancel the vote
                    post.downvote();

//                    try {
//                        voteRepo.deleteVotePost(user, post);
//                    } catch (SQLException e) {
//                        Logger.error("Could not delete vote post: " + e.getMessage());
//                        System.out.println("Failed to delete vote from the database.");
//                        return;
//                    }
                    post.getVotingUserID().remove(user.getUsername());
                } else { // changes from downvote to upvote
                    post.upvote();
                    post.upvote();
//                    try {
//                        voteRepo.updateVotePost(user, post, 1);
//                    } catch (SQLException e) {
//                        Logger.error("Could not update vote post: " + e.getMessage());
//                        System.out.println("Failed to update vote in the database.");
//                        return;
//                    }
                    post.getVotingUserID().put(user.getUsername(), 1);
                }
            } else { // new vote
                post.upvote();
//                try {
//                    voteRepo.saveVotePost(user, post, 1); // save the vote in the database
//                } catch (SQLException e) {
//                    Logger.error("Could not save vote post: " + e.getMessage());
//                    System.out.println("Failed to save vote to the database.");
//                    return;
//                }
                post.getVotingUserID().put(user.getUsername(), 1);
            }
        } else { // Downvote
            if (post.getVotingUserID().containsKey(user.getUsername())) {
                if (post.getVotingUserID().get(user.getUsername()).equals(-1)) {
                    post.upvote();

//                    try {
//                        voteRepo.deleteVotePost(user, post);
//                    } catch (SQLException e) {
//                        Logger.error("Could not delete vote post: " + e.getMessage());
//                        System.out.println("Failed to delete vote from the database.");
//                        return;
//                    }
                    post.getVotingUserID().remove(user.getUsername());
                } else {
                    post.downvote();
                    post.downvote();

//                    try {
//                        voteRepo.updateVotePost(user, post, -1);
//                    } catch (SQLException e) {
//                        Logger.error("Could not update vote post: " + e.getMessage());
//                        System.out.println("Failed to update vote in the database.");
//                        return;
//                    }
                    post.getVotingUserID().put(user.getUsername(), -1);
                }
            } else {
                post.downvote();
//                try {
//                    voteRepo.saveVotePost(user, post, -1); // save the vote in the database
//                } catch (SQLException e) {
//                    Logger.error("Could not save vote post: " + e.getMessage());
//                    System.out.println("Failed to save vote to the database.");
//                    return;
//                }
                post.getVotingUserID().put(user.getUsername(), -1);
            }
        }
    }

    public void voteComment(User user, Comment comment, boolean vote) {
        if(vote) {
            if(comment.votingUserID.containsKey(user.getUsername())) {
                if(comment.votingUserID.get(user.getUsername()).equals(1)) {
                    comment.downvote();

//                    try {
//                        voteRepo.deleteVoteComment(user, comment); // delete the vote from the database
//                    } catch (SQLException e) {
//                        Logger.error("Could not delete vote comment: " + e.getMessage());
//                        System.out.println("Failed to delete vote from the database.");
//                        return;
//                    }
                    comment.votingUserID.remove(user.getUsername());
                }
                else {
                    comment.upvote();
                    comment.upvote();
//
//                    try {
//                        voteRepo.updateVoteComment(user, comment, 1);
//                    } catch (SQLException e) {
//                        Logger.error("Could not update vote post: " + e.getMessage());
//                        System.out.println("Failed to update vote in the database.");
//                        return;
//                    }
                    comment.votingUserID.put(user.getUsername(), 1);
                }
            }
            else {
                comment.upvote();

//                try {
//                    voteRepo.saveVoteComment(user, comment, 1); // save the vote in the database
//                } catch (SQLException e) {
//                    Logger.error("Could not save vote comment: " + e.getMessage());
//                    System.out.println("Failed to save vote to the database.");
//                    return;
//                }
                comment.votingUserID.put(user.getUsername(), 1);
            }
        }
        else {
            if(comment.votingUserID.containsKey(user.getUsername())) {
                if(comment.votingUserID.get(user.getUsername()).equals(-1)) {
                    comment.upvote();

//                    try {
//                        voteRepo.deleteVoteComment(user, comment); // delete the vote from the database
//                    } catch (SQLException e) {
//                        Logger.error("Could not delete vote comment: " + e.getMessage());
//                        System.out.println("Failed to delete vote from the database.");
//                        return;
//                    }
                    comment.votingUserID.remove(user.getUsername());
                }
                else {
                    comment.downvote();
                    comment.downvote();

//                    try {
//                        voteRepo.updateVoteComment(user, comment, -1);
//                    } catch (SQLException e) {
//                        Logger.error("Could not update vote post: " + e.getMessage());
//                        System.out.println("Failed to update vote in the database.");
//                        return;
//                    }
                    comment.votingUserID.put(user.getUsername(), -1);
                }
            }
            else {
                comment.downvote();

//                try {
//                    voteRepo.saveVoteComment(user, comment, -1); // save the vote in the database
//                } catch (SQLException e) {
//                    Logger.error("Could not save vote comment: " + e.getMessage());
//                    System.out.println("Failed to save vote to the database.");
//                    return;
//                }
                comment.votingUserID.put(user.getUsername(), -1);
            }
        }
    }

    public void voteReply(User user, Comment reply, boolean vote) {
        if(vote) {
            if(reply.votingUserID.containsKey(user.getUsername())) {
                if(reply.votingUserID.get(user.getUsername()).equals(1)) {
                    reply.downvote();

//                    try {
//                        voteRepo.deleteVoteComment(user, reply); // delete the vote from the database
//                    } catch (SQLException e) {
//                        Logger.error("Could not delete vote reply: " + e.getMessage());
//                        System.out.println("Failed to delete vote from the database.");
//                        return;
//                    }
                    reply.votingUserID.remove(user.getUsername());
                }
                else {
                    reply.upvote();
                    reply.upvote();

//                    try {
//                        voteRepo.updateVoteComment(user, reply, 1);
//                    } catch (SQLException e) {
//                        Logger.error("Could not update vote post: " + e.getMessage());
//                        System.out.println("Failed to update vote in the database.");
//                        return;
//                    }
//                    reply.votingUserID.put(user.getUsername(), 1);
                }
            }
            else {
                reply.upvote();

//                try {
//                    voteRepo.saveVoteComment(user, reply, 1); // save the vote in the database
//                } catch (SQLException e) {
//                    Logger.error("Could not save vote reply: " + e.getMessage());
//                    System.out.println("Failed to save vote to the database.");
//                    return;
//                }
//                reply.votingUserID.put(user.getUsername(), 1);
            }
        }
        else {
            if(reply.votingUserID.containsKey(user.getUsername())) {
                if(reply.votingUserID.get(user.getUsername()).equals(-1)) {
                    reply.upvote();

//                    try {
//                        voteRepo.deleteVoteComment(user, reply); // delete the vote from the database
//                    } catch (SQLException e) {
//                        Logger.error("Could not delete vote reply: " + e.getMessage());
//                        System.out.println("Failed to delete vote from the database.");
//                        return;
//                    }
//                    reply.votingUserID.remove(user.getUsername());
                }
                else {
                    reply.downvote();
                    reply.downvote();

//                    try {
//                        voteRepo.updateVoteComment(user, reply, -1);
//                    } catch (SQLException e) {
//                        Logger.error("Could not update vote post: " + e.getMessage());
//                        System.out.println("Failed to update vote in the database.");
//                        return;
//                    }
//                    reply.votingUserID.put(user.getUsername(), -1);
                }
            }
            else {
                reply.downvote();

//                try {
//                    voteRepo.saveVoteComment(user, reply, -1); // save the vote in the database
//                } catch (SQLException e) {
//                    Logger.error("Could not save vote reply: " + e.getMessage());
//                    System.out.println("Failed to save vote to the database.");
//                    return;
//                }
//                reply.votingUserID.put(user.getUsername(), -1);
            }
        }
    }
}
