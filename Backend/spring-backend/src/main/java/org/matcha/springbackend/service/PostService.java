package org.matcha.springbackend.service;

import org.matcha.springbackend.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }
}
