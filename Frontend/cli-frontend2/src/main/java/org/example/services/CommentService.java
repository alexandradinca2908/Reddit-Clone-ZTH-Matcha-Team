package org.example.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.apiclients.ApiManager;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.userinterface.UIComment;

import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.util.List;

public class CommentService {
    private static CommentService instance;
    private final ApiManager apiManager;
    private UIComment uiComment = UIComment.getInstance();
    private final Gson gson;

    private CommentService(HttpClient client, Gson gson) {
        apiManager = ApiManager.getInstance(client);
        this.gson = gson;
    }

    public static CommentService getInstance(HttpClient client, Gson gson) {
        if (instance == null) {
            instance = new CommentService(client, gson);
        }
        return instance;
    }

    public void populatePost(Post post) {
        Type commentList = new TypeToken<List<Comment>>() {}.getType();
        apiManager.getApiCommentClient().setCurrentPostID(post.getId());
        List<Comment> comments = gson.fromJson(apiManager.getApiCommentClient().handleGet(), commentList);
        comments = comments.reversed();
        post.getComments().clear();
        post.getComments().addAll(comments);
    }

    public Comment openComment(Post post) {
        while (true) {
            int id = uiComment.pleaseEnterCommentId();
            for (Comment comment : post.getComments()) {
                if (comment.getDisplayId() == id) {
                    return comment;
                }
            }
            if (!uiComment.invalidId().equals("y")) {
                return null;
            }
        }
    }

    public Comment openReply(Comment comment) {
        while (true) {
            int id = uiComment.pleaseEnterCommentId();
            for (Comment reply : comment.getReplies()) {
                if (reply.getDisplayId() == id) {
                    return reply;
                }
            }
            if (!uiComment.invalidId().equals("y")) {
                return null;
            }
        }
    }

    public void createComment(Post post, User user) {
        String content = uiComment.getCommentDetailsFromUser();
        String author = user.getUsername();

        String json = String.format("""
                {
                    "content": "%s",
                    "author": "%s",
                    "parentId": null
                }
                """, content, author);

        apiManager.getApiCommentClient().setCurrentPostID(post.getId());
        Comment comment = gson.fromJson(apiManager.getApiCommentClient().handlePost(json), Comment.class);
        post.addComment(comment);
    }

    public int editComment(Post post, Comment comment, Comment parentComment) {
        String content = uiComment.getCommentDetailsFromUser();
        String json = String.format("""
                {
                    "content": "%s"
                }
                """, content);
        int idx = -1;
        Comment newComment = gson.fromJson(apiManager.getApiCommentClient().handlePut(json, comment.getId()), Comment.class);
        newComment.setDisplayId(comment.getDisplayId());
        if(parentComment == null) {
            idx = post.getComments().indexOf(comment);
            post.getComments().remove(comment);
            post.getComments().add(idx, newComment);

        } else {
            idx = parentComment.getReplies().indexOf(comment);
            parentComment.getReplies().remove(comment);
            parentComment.getReplies().add(idx, newComment);

        }

        return idx;
    }

    public void deleteComment(Post post, Comment comment, Comment parentComment) {
        String id = comment.getId();
        if (parentComment == null) {
            post.getComments().remove(comment);
        } else {
            parentComment.getReplies().remove(comment);
        }
        apiManager.getApiCommentClient().handleDelete(id);
    }

    public void createReply(Post post, Comment comment, User user) {
        String content = uiComment.getCommentDetailsFromUser();
        String author = user.getUsername();
        String parentId =  comment.getId();

        String json = String.format("""
                {
                    "content": "%s",
                    "author": "%s",
                    "parentId": "%s"
                }
                """, content, author, parentId);

        apiManager.getApiCommentClient().setCurrentPostID(post.getId());
        Comment reply = gson.fromJson(apiManager.getApiCommentClient().handlePost(json), Comment.class);
        comment.addReply(reply);
    }

    public void upvote(Comment comment) {
        String json = """
                {
                    "voteType": "up"
                }
                """;
        switch (comment.getUserVote()) {
            case "UP":
                comment.setUserVote("NONE");
                comment.setScore(comment.getScore() - 1);
                break;

            case "DOWN":
                comment.setUserVote("UP");
                comment.setScore(comment.getScore() + 2);
                break;

            case "NONE":
                comment.setUserVote("UP");
                comment.setScore(comment.getScore() + 1);
                break;

            default:
                break;

        }
        apiManager.getApiCommentClient().handleVote(comment.getId(), json);
    }

    public void downvote(Comment comment) {
        String json = """
                {
                    "voteType": "down"
                }
                """;
        switch (comment.getUserVote()) {
            case "DOWN":
                comment.setUserVote("NONE");
                comment.setScore(comment.getScore() + 1);
                break;

            case "UP":
                comment.setUserVote("DOWN");
                comment.setScore(comment.getScore() - 2);
                break;

            case "NONE":
                comment.setUserVote("DOWN");
                comment.setScore(comment.getScore() - 1);
                break;

            default:
                break;

        }
        apiManager.getApiCommentClient().handleVote(comment.getId(), json);
    }
}
