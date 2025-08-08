package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.post.PostDto;
import org.matcha.springbackend.dto.subreddit.SubredditDto;
import org.matcha.springbackend.dto.subreddit.requestbody.CreateSubredditBodyDto;
import org.matcha.springbackend.dto.subreddit.requestbody.UpdateSubredditBodyDto;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.mapper.SubredditMapper;
import org.matcha.springbackend.model.Subreddit;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.service.PostService;
import org.matcha.springbackend.service.SubredditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subreddits")
public class SubredditController {
    private final SubredditService subredditService;
    private final SubredditMapper subredditMapper;
    private final PostService postService;
    private final PostMapper postMapper;

    public SubredditController(SubredditService subredditService, SubredditMapper subredditMapper,
                               PostService postService, PostMapper postMapper) {
        this.subredditService = subredditService;
        this.subredditMapper = subredditMapper;
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<SubredditDto>>> getSubreddits() {
        //  Map Post to PostDTO
        List<SubredditDto> subredditDtos = subredditService.getSubreddits().stream()
                .map(subredditMapper::modelToDto)
                .toList();

        DataResponse<List<SubredditDto>> dataResponse = new DataResponse<>(true, subredditDtos);
        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping
    public ResponseEntity<DataResponse<SubredditDto>> createSubreddit(@RequestBody CreateSubredditBodyDto subredditDTO) {
        Subreddit subreddit = subredditService.addSubreddit(subredditDTO);

        DataResponse<SubredditDto> dataResponse = new DataResponse<>(true,
                subredditMapper.modelToDto(subreddit));
        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("/{name}")
    public ResponseEntity<DataResponse<SubredditDto>> getSubredditByName(@PathVariable String name) {
        Subreddit subreddit = subredditService.getSubredditByName(name);
        if (subreddit == null) {
            return ResponseEntity.notFound().build();
        }
        SubredditDto subredditDto = subredditMapper.modelToDto(subreddit);
        DataResponse<SubredditDto> dataResponse = new DataResponse<>(true, subredditDto);
        return ResponseEntity.ok(dataResponse);
    }

    @PutMapping("/{name}")
    public ResponseEntity<DataResponse<SubredditDto>> updateSubreddit(
            @PathVariable String name,
            @RequestBody UpdateSubredditBodyDto updateDto) {
        Subreddit updated = subredditService.updateSubreddit(name, updateDto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        SubredditDto subredditDto = subredditMapper.modelToDto(updated);
        DataResponse<SubredditDto> dataResponse = new DataResponse<>(true, subredditDto);
        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping("/{name}/posts")
    public ResponseEntity<DataResponse<List<PostDto>>> getPostsBySubreddit(@PathVariable String name) {
        List<PostDto> posts = postService.getPostsBySubredditName(name).stream()
                .map(postMapper::modelToDto)
                .toList();
        return ResponseEntity.ok(new DataResponse<>(true, posts));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<DataResponse<String>> deleteSubreddit(@PathVariable String name) {
        try {
            subredditService.deleteSubredditIfNoPosts(name);
            DataResponse<String> response = new DataResponse<>(true, "Subreddit deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ResponseStatusException ex) {
            if (ex.getStatusCode() == HttpStatus.CONFLICT) {
                DataResponse<String> response = new DataResponse<>(false, "Subreddit cannot be deleted because it has posts");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            } else if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                DataResponse<String> response = new DataResponse<>(false, "Subreddit not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            throw ex;
        }
    }
}
