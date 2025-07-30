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
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    public PostService(PostMapper postMapper, PostRepository postRepository) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        List<PostEntity> entities = postRepository.findAll();
        return entities.stream()
                .map(postMapper::entityToModel)
                .collect(Collectors.toList());
    }

    public void addPost(Post post) {
        PostEntity entity = postMapper.modelToEntity(post);
        postRepository.save(entity);
    }

public void updatePost(Post post) {
        PostEntity entity = postMapper.modelToEntity(post);
        if (postRepository.existsById(entity.getPostID())) {
            postRepository.save(entity);
        } else {
            throw new IllegalArgumentException("Post with ID " + entity.getPostID() + " does not exist.");
        }
    }

    public Post getPostById(String id) {
        return postRepository.findById(java.util.UUID.fromString(id))
                .map(postMapper::entityToModel)
                .orElse(null);
    }

    public boolean deletePost(String id) {
        if (!postRepository.existsById(java.util.UUID.fromString(id))) {
            return false;
        }
        postRepository.deleteById(java.util.UUID.fromString(id));
        return true;
    }

    //  TODO
    public boolean votePost(String id, String vote) {
        Post post = getPostById(id);
        if (post == null) {
            return false;
        }
        // Implement vote logic here
        return true;
    }
}
