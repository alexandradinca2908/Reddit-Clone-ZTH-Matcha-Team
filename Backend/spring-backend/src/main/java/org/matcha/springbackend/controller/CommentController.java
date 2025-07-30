package org.matcha.springbackend.controller;

import org.matcha.springbackend.dto.comment.CommentDTO;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.mapper.PostMapper;
import org.matcha.springbackend.mapper.VoteMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class CommentController {
    private final PostMapper postMapper;
    private final AccountMapper accountMapper;
    private final VoteMapper voteMapper;
    private final CommentMapper commentMapper;

    public CommentController(PostMapper postMapper, AccountMapper accountMapper, VoteMapper voteMapper, CommentMapper commentMapper) {
        this.postMapper = postMapper;
        this.accountMapper = accountMapper;
        this.voteMapper = voteMapper;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/{postId}/comments")
    public List<CommentDTO> getComments(@PathVariable String postId) {
        return null;
    }
}
