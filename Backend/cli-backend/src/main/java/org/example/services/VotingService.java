package org.example.services;

import org.example.dbconnection.DatabaseConnection;
import org.example.loggerobjects.Logger;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.repositories.CommentRepo;
import org.example.repositories.VoteRepo;
import org.example.userinterface.UIPost;
import org.example.userinterface.UIVote;

import java.sql.SQLException;

public class VotingService {
    private static VotingService instance;
    private static final VoteRepo voteRepo = VoteRepo.getInstance();
    private static final UIVote uiVote = UIVote.getInstance();

    private VotingService() {}

    public static VotingService getInstance() {
        if (instance == null) {
            instance = new VotingService();

            try {
                voteRepo.load();
            } catch (SQLException e) {
                Logger.error("Could not load votes from the database: " + e.getMessage());
                DatabaseConnection.cannotConnect();
            }
        }
        return instance;
    }

    public void votePost(User user, Post post, boolean vote) {
        if (vote) { // Upvote
            if (post.getVotingUserID().containsKey(user.getUsername())) { // the user has already voted
                if (post.getVotingUserID().get(user.getUsername()).equals(1)) { // cancel the vote
                    post.downvote();

                    try {
                        voteRepo.deleteVotePost(user, post);
                    } catch (SQLException e) {
                        Logger.error("Could not delete vote post: " + e.getMessage());
                        uiVote.printVoteError("delete");
                        return;
                    }
                    post.getVotingUserID().remove(user.getUsername());
                } else { // changes from downvote to upvote
                    post.upvote();
                    post.upvote();
                    try {
                        voteRepo.updateVotePost(user, post, 1);
                    } catch (SQLException e) {
                        Logger.error("Could not update vote post: " + e.getMessage());
                        uiVote.printVoteError("update");
                        return;
                    }
                    post.getVotingUserID().put(user.getUsername(), 1);
                }
            } else { // new vote
                post.upvote();
                try {
                    voteRepo.saveVotePost(user, post, 1); // save the vote in the database
                } catch (SQLException e) {
                    Logger.error("Could not save vote post: " + e.getMessage());
                    uiVote.printVoteError("save");
                    return;
                }
                post.getVotingUserID().put(user.getUsername(), 1);
            }
        } else { // Downvote
            if (post.getVotingUserID().containsKey(user.getUsername())) {
                if (post.getVotingUserID().get(user.getUsername()).equals(-1)) {
                    post.upvote();

                    try {
                        voteRepo.deleteVotePost(user, post);
                    } catch (SQLException e) {
                        Logger.error("Could not delete vote post: " + e.getMessage());
                        uiVote.printVoteError("delete");
                        return;
                    }
                    post.getVotingUserID().remove(user.getUsername());
                } else {
                    post.downvote();
                    post.downvote();

                    try {
                        voteRepo.updateVotePost(user, post, -1);
                    } catch (SQLException e) {
                        Logger.error("Could not update vote post: " + e.getMessage());
                        uiVote.printVoteError("update");
                        return;
                    }
                    post.getVotingUserID().put(user.getUsername(), -1);
                }
            } else {
                post.downvote();
                try {
                    voteRepo.saveVotePost(user, post, -1); // save the vote in the database
                } catch (SQLException e) {
                    Logger.error("Could not save vote post: " + e.getMessage());
                    uiVote.printVoteError("save");
                    return;
                }
                post.getVotingUserID().put(user.getUsername(), -1);
            }
        }
    }

    public void voteComment(User user, Comment comment, boolean vote) {
        if (vote) {
            if (comment.votingUserID.containsKey(user.getUsername())) {
                if (comment.votingUserID.get(user.getUsername()).equals(1)) {
                    comment.downvote();

                    try {
                        voteRepo.deleteVoteComment(user, comment); // delete the vote from the database
                    } catch (SQLException e) {
                        Logger.error("Could not delete vote comment: " + e.getMessage());
                        uiVote.printVoteError("delete");
                        return;
                    }
                    comment.votingUserID.remove(user.getUsername());
                } else {
                    comment.upvote();
                    comment.upvote();

                    try {
                        voteRepo.updateVoteComment(user, comment, 1);
                    } catch (SQLException e) {
                        Logger.error("Could not update vote post: " + e.getMessage());
                        uiVote.printVoteError("update");
                        return;
                    }
                    comment.votingUserID.put(user.getUsername(), 1);
                }
            } else {
                comment.upvote();

                try {
                    voteRepo.saveVoteComment(user, comment, 1); // save the vote in the database
                } catch (SQLException e) {
                    Logger.error("Could not save vote comment: " + e.getMessage());
                    uiVote.printVoteError("save");
                    return;
                }
                comment.votingUserID.put(user.getUsername(), 1);
            }
        } else {
            if (comment.votingUserID.containsKey(user.getUsername())) {
                if (comment.votingUserID.get(user.getUsername()).equals(-1)) {
                    comment.upvote();

                    try {
                        voteRepo.deleteVoteComment(user, comment); // delete the vote from the database
                    } catch (SQLException e) {
                        Logger.error("Could not delete vote comment: " + e.getMessage());
                        uiVote.printVoteError("delete");
                        return;
                    }
                    comment.votingUserID.remove(user.getUsername());
                } else {
                    comment.downvote();
                    comment.downvote();

                    try {
                        voteRepo.updateVoteComment(user, comment, -1);
                    } catch (SQLException e) {
                        Logger.error("Could not update vote post: " + e.getMessage());
                        uiVote.printVoteError("update");
                        return;
                    }
                    comment.votingUserID.put(user.getUsername(), -1);
                }
            } else {
                comment.downvote();

                try {
                    voteRepo.saveVoteComment(user, comment, -1); // save the vote in the database
                } catch (SQLException e) {
                    Logger.error("Could not save vote comment: " + e.getMessage());
                    uiVote.printVoteError("save");
                    return;
                }
                comment.votingUserID.put(user.getUsername(), -1);
            }
        }
    }
}
