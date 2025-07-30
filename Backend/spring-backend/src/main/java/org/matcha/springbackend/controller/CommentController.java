package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.comment.CommentDTO;
import org.matcha.springbackend.dto.post.PostDTO;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.repositories.CommentRepository;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public CommentController(PostMapper postMapper, AccountMapper accountMapper, VoteMapper voteMapper,
                             CommentMapper commentMapper, CommentService commentService) {
        this.postMapper = postMapper;
        this.accountMapper = accountMapper;
        this.voteMapper = voteMapper;
        this.commentMapper = commentMapper;
        this.commentService = commentService;
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

}
