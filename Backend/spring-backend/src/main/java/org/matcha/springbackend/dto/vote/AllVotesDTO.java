package org.matcha.springbackend.dto.vote;

public record AllVotesDTO(
        Integer upvotes,
        Integer downvotes,
        Integer score,
        String userVote
) {
}
