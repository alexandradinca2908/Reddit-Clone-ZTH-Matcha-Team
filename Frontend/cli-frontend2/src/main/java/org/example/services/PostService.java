package org.example.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.apiclients.ApiManager;
import org.example.models.Post;
import org.example.models.Subreddit;
import org.example.models.User;
import org.example.userinterface.UIPost;

import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

public class PostService {
    private static PostService instance;
    private final UIPost uiPost = UIPost.getInstance();
    private final ApiManager apiManager;
    private final Gson gson;

    private PostService(HttpClient client, Gson gson) {
        apiManager = ApiManager.getInstance(client);
        this.gson = gson;
    }

    public static PostService getInstance(HttpClient client, Gson gson) {
        if (instance == null) {
            instance = new PostService(client, gson);
        }
        return instance;
    }

    public Post openPost(Subreddit subreddit) {
        while (true) {
            String id = uiPost.pleaseEnterPostId();
            for (Post post : subreddit.getPosts()) {
                if (post.getDisplayId().equals(id)) {
                    return post;
                }
            }
            if (!uiPost.invalidId().equals("y")) {
                return null;
            }
        }
    }

    public void upvote(Post post) {
        String json = """
                {
                    "voteType": "up"
                }
                """;
        switch (post.getUserVote()) {
            case "up":
                post.setUserVote("none");
                post.setScore(post.getScore() - 1);
                break;

            case "down":
                post.setUserVote("up");
                post.setScore(post.getScore() + 2);
                break;

            case "none":
                post.setUserVote("up");
                post.setScore(post.getScore() + 1);
                break;

            default:
                break;

        }
        apiManager.getApiPostClient().handleVote(post.getId(), json);
    }

    public void downvote(Post post) {
        String json = """
                {
                    "voteType": "down"
                }
                """;
        switch (post.getUserVote()) {
            case "down":
                post.setUserVote("none");
                post.setScore(post.getScore() + 1);
                break;

            case "up":
                post.setUserVote("down");
                post.setScore(post.getScore() - 2);
                break;

            case "none":
                post.setUserVote("down");
                post.setScore(post.getScore() - 1);
                break;

            default:
                break;

        }
        apiManager.getApiPostClient().handleVote(post.getId(), json);
    }

    public void populateSubreddit(Subreddit subreddit) {
        Type postList = new TypeToken<List<Post>>() {
        }.getType();
        List<Post> posts = gson.fromJson(apiManager.getApiPostClient().handleGet(), postList);
        posts = posts.reversed();
        subreddit.getPosts().clear();
        subreddit.getPosts().addAll(posts);
    }

    public void createPost(Subreddit subreddit, User user) {
        ArrayList<String> postDetails = uiPost.getPostDetailsFromUser(user);
        String json = String.format("""
                {
                    "title" : "%s",
                    "content" : "%s",
                    "author" : "%s",
                    "subreddit" : "%s",
                }
                """, postDetails.get(0), postDetails.get(1), postDetails.get(2), postDetails.get(3));

        Post post = gson.fromJson(apiManager.getApiPostClient().handlePost(json), Post.class);
        subreddit.addPost(post);
    }


}
