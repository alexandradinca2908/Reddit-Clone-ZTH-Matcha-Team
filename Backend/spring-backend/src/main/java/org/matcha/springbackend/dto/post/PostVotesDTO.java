package org.matcha.springbackend.dto.post;

public record PostVotesDTO(
        Integer upvotes,
        Integer downvotes,
        Integer score,
        String userVote
) {
}
