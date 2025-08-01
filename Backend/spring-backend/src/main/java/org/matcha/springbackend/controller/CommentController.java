package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.comment.CommentDto;
import org.matcha.springbackend.dto.comment.requestbody.AddCommentBodyDTO;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class CommentController {
    private final PostMapper postMapper;
    private final AccountMapper accountMapper;
    private final VoteMapper voteMapper;
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final AccountService accountService;

    public CommentController(PostMapper postMapper, AccountMapper accountMapper, VoteMapper voteMapper,
                             CommentMapper commentMapper, CommentService commentService, AccountService accountService) {
        this.postMapper = postMapper;
        this.accountMapper = accountMapper;
        this.voteMapper = voteMapper;
        this.commentMapper = commentMapper;
        this.commentService = commentService;
        this.accountService = accountService;
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<DataResponse<List<CommentDto>>> getCommentsFromPost(@PathVariable String postId) {
        //  Map Comments to CommentDTOs
        List<CommentDto> commentDtos = commentService.getCommentsByPostId(UUID.fromString(postId))
                .stream()
                .map(commentMapper::modelToDto)
                .toList();

        DataResponse<List<CommentDto>> dataResponse = new DataResponse<>(true, commentDtos);
        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<DataResponse<CommentDto>> addCommentToPost(@PathVariable String postId,
                                                                     @RequestBody AddCommentBodyDTO commentDTO) {
        OffsetDateTime createdAt = OffsetDateTime.now();
        Comment comment = new Comment(UUID.randomUUID(), accountService.getCurrentAccount(), null,
                UUID.fromString(postId), commentDTO.content(), false, 0, 0, createdAt, createdAt);

        commentService.addCommentToPost(comment);

        DataResponse<CommentDto> dataResponse = new DataResponse<>(true, commentMapper.modelToDto(comment));
        return ResponseEntity.ok(dataResponse);
    }

}
