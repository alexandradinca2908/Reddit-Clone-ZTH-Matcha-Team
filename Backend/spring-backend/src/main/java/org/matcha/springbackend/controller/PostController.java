package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.PostDTO;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        //  TODO retrieve from DB in post array (located in postService)
        //  TODO - just delete this if not necessary
        
        //  Map Post to PostDTO
        List<PostDTO> postDTOs = postService.getPosts().stream()
                .map(postMapper::modelToDTO)
                .toList();

        DataResponse<List<PostDTO>> dataResponse = new DataResponse<>(true, postDTOs);

        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<DataResponse<PostDTO>> getPostById(@PathVariable String id) {
        //  TODO - retrieve from DB in post array (located in postService)
        //  TODO - just delete this if not necessary

        PostDTO postDTO = postService.getPosts().stream()
                .filter(post -> post.getPostID().toString().equals(id))
                .findFirst()
                .map(postMapper::modelToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Post with UUID %s not found", id) ));

        DataResponse<PostDTO> dataResponse = new DataResponse<>(true, postDTO);

        return ResponseEntity.ok(dataResponse);
    }
}
