package org.example.models;

import java.util.ArrayList;

public class Subreddit {
    private String id;
    private String name;
    private String displayName;
    private String description;
    private int memberCount;
    private int postCount;
    private String iconUrl;
    private String createdAt;
    private ArrayList<Post> posts;

    public Subreddit(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
        this.description = "Cel mai tare sub";
        this.memberCount = 1;
        this.posts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void updatePostCount() {
        postCount = posts.size();
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}
