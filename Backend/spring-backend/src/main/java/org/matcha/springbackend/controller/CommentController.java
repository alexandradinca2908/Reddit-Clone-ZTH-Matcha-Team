package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.comment.CommentDto;
import org.matcha.springbackend.dto.comment.requestbody.AddCommentBodyDTO;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.response.DataResponse;
import org.matcha.springbackend.service.CommentService;
import org.matcha.springbackend.session.AccountSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class CommentController {
    private final CommentMapper commentMapper;
    private final CommentService commentService;

    public CommentController(CommentMapper commentMapper, CommentService commentService) {
        this.commentMapper = commentMapper;
        this.commentService = commentService;
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<DataResponse<List<CommentDto>>> getCommentsFromPost(@PathVariable String postId) {
        //  Map Comments to CommentDTOs
        List<CommentDto> commentDtos = commentService.getCommentsByPostId(UUID.fromString(postId))
                .stream()
                .map(commentMapper::modelToDto)
                .toList();

        DataResponse<List<CommentDto>> dataResponse = new DataResponse<>(true, commentDtos);
        return ResponseEntity.ok(dataResponse);
    }

    //  TODO
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<DataResponse<CommentDto>> addCommentToPost(@PathVariable String postId,
                                                                     @RequestBody AddCommentBodyDTO commentDTO) {
        Comment comment;
        try {
            comment = commentService.addCommentToPost(postId, commentDTO);
        } catch (ResponseStatusException e) {
            throw e;
        }  catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

        DataResponse<CommentDto> dataResponse = new DataResponse<>(true, commentMapper.modelToDto(comment));
        return ResponseEntity.ok(dataResponse);
    }

}
