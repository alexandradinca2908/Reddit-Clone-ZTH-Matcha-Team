package org.matcha.springbackend.service;

import org.matcha.springbackend.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final List<Post> posts;

    public PostService(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}
