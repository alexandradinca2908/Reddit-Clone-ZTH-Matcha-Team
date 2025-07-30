package org.matcha.springbackend.dto.account;

public record AccountDTO(
        String accountId,
        String username,
        String email,
        String photoPath,
        boolean isDeleted,
        String createdAt,
        String updatedAt
) {
    @Override
    public String accountId() {
        return accountId;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String photoPath() {
        return photoPath;
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
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
