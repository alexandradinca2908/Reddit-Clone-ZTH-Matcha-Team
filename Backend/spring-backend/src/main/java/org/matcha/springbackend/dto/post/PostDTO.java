package org.matcha.springbackend.dto.post;

public record PostDTO(
        String id,
        String title,
        String content,
        String author,
        String subreddit,
        Integer upvotes,
        Integer downvotes,
        Integer score,
        Integer commentCount,
        String userVote,
        String createdAt,
        String updatedAt
) {
    @Override
    public String id() {
        return id;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String content() {
        return content;
    }

    @Override
    public String author() {
        return author;
    }

    @Override
    public String subreddit() {
        return subreddit;
    }

    @Override
    public Integer upvotes() {
        return upvotes;
    }

    @Override
    public Integer downvotes() {
        return downvotes;
    }

    @Override
    public Integer score() {
        return score;
    }

    @Override
    public Integer commentCount() {
        return commentCount;
    }

    @Override
    public String userVote() {
        return userVote;
    }

    @Override
    public String createdAt() {
        return createdAt;
    }

    @Override
    public String updatedAt() {
        return updatedAt;
    }
}
