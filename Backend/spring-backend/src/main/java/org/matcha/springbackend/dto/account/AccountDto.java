package org.matcha.springbackend.dto.account;

public record AccountDto(
        String accountId,
        String username,
        String email,
        String photoPath,
        boolean isDeleted,
        String createdAt,
        String updatedAt
) {
}
