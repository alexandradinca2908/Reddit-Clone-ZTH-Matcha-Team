package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.PostDTO;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.service.PostService;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public RequestEntity<DataResponse<List<PostDTO>>> getPosts() {
        //  TODO retrieve from DB

        //  Map Post to PostDTO
        return null;
    }
}
