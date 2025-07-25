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

    public Post getPostById(String id) {
        for (Post post : posts) {
            if (post.getPostID().toString().equals(id)) {
                return post;
            }
        }

        return null;
    }

    public boolean deletePost(String id) {
        return posts.remove(getPostById(id));
    }
}
