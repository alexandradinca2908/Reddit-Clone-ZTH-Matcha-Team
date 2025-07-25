package org.matcha.springbackend.service;

import org.matcha.springbackend.entities.PostEntity;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final List<Post> posts;
    private final PostRepository postRepository;

    public PostService(List<Post> posts, PostRepository postRepository) {
        this.posts = posts;
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        List<PostEntity> entities = postRepository.findAll(); // Optionally: use `@EntityGraph` to fetch relations
        return entities.stream()
                .map(PostMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper::toDomain)
                .collect(Collectors.toList());
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
