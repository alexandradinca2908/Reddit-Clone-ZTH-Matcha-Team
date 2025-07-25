package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.PostDTO;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.service.PostService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<PostDTO>>> getPosts() {
        //  TODO retrieve from DB

        //  Map Post to PostDTO
        List<PostDTO> postDTOs = postService.getPosts().stream()
                .map(postMapper::modelToDTO)
                .toList();

        DataResponse<List<PostDTO>> dataResponse = new DataResponse<>(true, postDTOs);

        return ResponseEntity.ok(dataResponse);
    }
}
