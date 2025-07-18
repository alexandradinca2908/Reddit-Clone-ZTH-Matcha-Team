package org.example.repositories;

import org.example.dbconnection.DatabaseConnection;
import org.example.loggerobjects.Logger;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.services.PostService;
import org.example.services.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*

CREATE TABLE post_vote (
    username TEXT REFERENCES profile(username) ON DELETE CASCADE,
    postID INTEGER REFERENCES post(postID) ON DELETE CASCADE,
    vote_type INTEGER CHECK (vote_type IN (1, -1)) NOT NULL,
    PRIMARY KEY (username, postID)
);

CREATE TABLE comment_vote (
    username TEXT REFERENCES profile(username) ON DELETE CASCADE,
    commentID INTEGER REFERENCES comment(commentID) ON DELETE CASCADE,
    vote_type INTEGER CHECK (vote_type IN (1, -1)) NOT NULL,
    PRIMARY KEY (username, commentID)
);

 */

public class VoteRepo {
    private static VoteRepo instance;

    public static VoteRepo getInstance() {
        if (instance == null) {
            instance = new VoteRepo();
        }
        return instance;
    }

    public void load() throws SQLException {
//        loadPostVotes();
        loadCommentVotes();
    }

//    private void loadPostVotes() throws SQLException {
//        if (!DatabaseConnection.isConnected()) {
//            return;
//        }
//
//        String sql = "SELECT username, postID, vote_type FROM post_vote";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             ResultSet rs = pstmt.executeQuery()) {
//
//            while (rs.next()) {
//                String username = rs.getString("username");
//                int postID = rs.getInt("postID");
//                int voteType = rs.getInt("vote_type");
//
//                User user = UserService.findByUsername(username);
//                Post post = PostService.findById(postID);
//                if (user != null && post != null) {
//                    if (voteType == 1)
//                        post.upvote();
//                    else if (voteType == -1)
//                        post.downvote();
//
//                    post.getVotingUserID().put(username, voteType);
//                } else {
//                    Logger.error("User " + username + " not found");
//                }
//            }
//        }
//    }

    private void loadCommentVotes() throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = "SELECT username, commentID, vote_type FROM comment_vote";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                int commentID = rs.getInt("commentID");
                int voteType = rs.getInt("vote_type");

                User user = UserService.findByUsername(username);
                Comment comment = Comment.findById(commentID);
                if (user != null && comment != null) {
                    if (voteType == 1)
                        comment.upvote();
                    else if (voteType == -1)
                        comment.downvote();

                    comment.votingUserID.put(username, voteType);
                } else {
                    Logger.error("User " + username + " not found");
                }
            }
        }
    }

//    public void saveVotePost(User user, Post post, int voteType) throws SQLException {
//        if (!DatabaseConnection.isConnected()) {
//            return;
//        }
//
//        String sql = "INSERT INTO post_vote (username, postID, vote_type) VALUES (?, ?, ?)";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, user.getUsername());
//            pstmt.setInt(2, post.getPostID());
//            pstmt.setInt(3, voteType);
//            pstmt.executeUpdate();
//        }
//    }

    public void saveVoteComment(User user, Comment comment, int voteType) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = "INSERT INTO comment_vote (username, commentID, vote_type) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setInt(2, comment.getCommentID());
            pstmt.setInt(3, voteType);
            pstmt.executeUpdate();
        }
    }

//    public void deleteVotePost(User user, Post post) throws SQLException {
//        if (!DatabaseConnection.isConnected()) {
//            return;
//        }
//
//        String sql = "DELETE FROM post_vote WHERE username = ? AND postID = ?";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, user.getUsername());
//            pstmt.setInt(2, post.getPostID());
//            pstmt.executeUpdate();
//        }
//    }

    public void deleteVoteComment(User user, Comment comment) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = "DELETE FROM comment_vote WHERE username = ? AND commentID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setInt(2, comment.getCommentID());
            pstmt.executeUpdate();
        }
    }

//    public void updateVotePost(User user, Post post, int voteType) throws SQLException {
//        if (!DatabaseConnection.isConnected()) {
//            return;
//        }
//
//        String sql = "UPDATE post_vote SET vote_type = ? WHERE username = ? AND postID = ?";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setInt(1, voteType);
//            pstmt.setString(2, user.getUsername());
//            pstmt.setInt(3, post.getPostID());
//            pstmt.executeUpdate();
//        }
//    }

    public void updateVoteComment(User user, Comment comment, int voteType) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = "UPDATE comment_vote SET vote_type = ? WHERE username = ? AND commentID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, voteType);
            pstmt.setString(2, user.getUsername());
            pstmt.setInt(3, comment.getCommentID());
            pstmt.executeUpdate();
        }
    }

}
