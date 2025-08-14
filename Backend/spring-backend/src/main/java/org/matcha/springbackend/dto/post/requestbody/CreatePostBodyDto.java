package org.matcha.springbackend.dto.post.requestbody;

import org.springframework.web.multipart.MultipartFile;

public record CreatePostBodyDto(
        String title,
        String content,
        String author,
        String subreddit,
        MultipartFile image,
        String filter
) {
}
