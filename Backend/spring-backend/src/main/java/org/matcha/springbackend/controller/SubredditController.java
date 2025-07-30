package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.subreddit.SubredditDTO;
import org.matcha.springbackend.dto.subreddit.requestbody.CreateSubredditBodyDTO;
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
    public ResponseEntity<DataResponse<List<SubredditDTO>>> getSubreddits() {
        //  Map Post to PostDTO
        List<SubredditDTO> subredditDTOs = subredditService.getSubreddits().stream()
                .map(subredditMapper::modelToDTO)
                .toList();

        DataResponse<List<SubredditDTO>> dataResponse = new DataResponse<>(true, subredditDTOs);
        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping
    public ResponseEntity<DataResponse<SubredditDTO>> createSubreddit(@RequestBody CreateSubredditBodyDTO subredditDTO) {
        //  Create post fields
        UUID uuid = UUID.randomUUID();
        OffsetDateTime createdAt = OffsetDateTime.now();

        //  Create and add post
        Subreddit subreddit = new Subreddit(uuid, subredditDTO.name(), subredditDTO.displayName(),
                subredditDTO.description(), false, 0, 0,
                subredditDTO.iconUrl(), createdAt);

        subredditService.addSubreddit(subreddit);

        //  TODO - UPDATE IN DB

        //  Send response
        DataResponse<SubredditDTO> dataResponse = new DataResponse<>(true,
                subredditMapper.modelToDTO(subreddit));
        return ResponseEntity.ok(dataResponse);
    }
}
