package org.matcha.springbackend.dto.vote;

public record AllVotesDto(
        Integer upvotes,
        Integer downvotes,
        Integer score,
        String userVote
) {
}
