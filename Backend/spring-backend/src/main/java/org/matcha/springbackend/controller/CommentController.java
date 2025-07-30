package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.comment.CommentDTO;
import org.matcha.springbackend.dto.comment.requestbody.AddCommentBodyDTO;
import org.matcha.springbackend.dto.post.PostDTO;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.repositories.CommentRepository;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
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
    public ResponseEntity<DataResponse<List<CommentDTO>>> getCommentsFromPost(@PathVariable String postId) {
        //  Map Comments to CommentDTOs
        List<CommentDTO> commentDTOS = commentService.getCommentsByPostId(UUID.fromString(postId))
                .stream()
                .map(commentMapper::modelToDTO)
                .toList();

        DataResponse<List<CommentDTO>> dataResponse = new DataResponse<>(true, commentDTOS);
        return ResponseEntity.ok(dataResponse);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<DataResponse<CommentDTO>> addCommentToPost(@PathVariable String postId,
                                                                     @RequestBody AddCommentBodyDTO commentDTO) {
        OffsetDateTime createdAt = OffsetDateTime.now();
        Comment comment = new Comment(UUID.randomUUID(), accountService.getCurrentAccount(), null,
                UUID.fromString(postId), commentDTO.content(), false, 0, 0, createdAt, createdAt);

        commentService.addCommentToPost(comment);

        DataResponse<CommentDTO> dataResponse = new DataResponse<>(true, commentMapper.modelToDTO(comment));
        return ResponseEntity.ok(dataResponse);
    }

}
