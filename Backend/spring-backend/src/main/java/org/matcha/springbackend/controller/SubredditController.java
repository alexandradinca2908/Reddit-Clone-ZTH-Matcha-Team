package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.subreddit.SubredditDto;
import org.matcha.springbackend.dto.subreddit.requestbody.CreateSubredditBodyDto;
import org.matcha.springbackend.mapper.SubredditMapper;
import org.matcha.springbackend.model.Subreddit;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.service.SubredditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subreddits")
public class SubredditController {
    private final SubredditService subredditService;
    private final SubredditMapper subredditMapper;

    public SubredditController(List<Subreddit> subreddits, SubredditService subredditService, SubredditMapper subredditMapper) {
        this.subredditService = subredditService;
        this.subredditMapper = subredditMapper;
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
        //  Create post fields
        UUID uuid = UUID.randomUUID();
        OffsetDateTime createdAt = OffsetDateTime.now();

        //  Create and add post
        Subreddit subreddit = new Subreddit(uuid, subredditDTO.name(), subredditDTO.displayName(),
                subredditDTO.description(), false, 0, 0,
                subredditDTO.iconUrl(), createdAt);

        subredditService.addSubreddit(subreddit);

        //  Send response
        DataResponse<SubredditDto> dataResponse = new DataResponse<>(true,
                subredditMapper.modelToDto(subreddit));
        return ResponseEntity.ok(dataResponse);
    }
}
