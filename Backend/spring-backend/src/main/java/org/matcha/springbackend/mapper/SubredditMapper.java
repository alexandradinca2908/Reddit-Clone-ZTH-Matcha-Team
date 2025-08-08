package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.subreddit.SubredditDto;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.model.Subreddit;
import org.matcha.springbackend.repository.AccountRepository;
import org.matcha.springbackend.session.AccountSession;
import org.springframework.stereotype.Component;
import org.matcha.springbackend.entities.SubredditEntity;

@Component
public class SubredditMapper {

    private final AccountSession accountSession;
    private final AccountRepository accountRepository;

    public SubredditMapper(AccountSession accountSession, AccountRepository accountRepository) {
        this.accountSession = accountSession;
        this.accountRepository = accountRepository;
    }

    public SubredditDto modelToDto(Subreddit model) {
        String id = model.getId().toString();
        String name = model.getName();
        String displayName = model.getDisplayName();
        String description = model.getDescription();
        Integer memberCount = model.getMemberCount();
        Integer postCount = model.getPostCount();
        String iconURL = model.getIconUrl();
        String createdAt = model.getCreatedAt().toString();

        return new SubredditDto(id, name, displayName, description, memberCount,
                postCount, iconURL, createdAt);
    }

    public Subreddit entityToModel(SubredditEntity entity) {
        if (entity == null) return null;

        Subreddit subreddit = new Subreddit();
        subreddit.setId(entity.getSubredditId());
        subreddit.setName(entity.getName());
        subreddit.setAccount(accountSession.getCurrentAccount());
        subreddit.setDisplayName(entity.getDisplayName());
        subreddit.setDescription(entity.getDescription());
        subreddit.setDeleted(entity.isDeleted());
        subreddit.setCreatedAt(entity.getCreatedAt());
        subreddit.setMemberCount(entity.getMemberCount());
        subreddit.setPostCount(entity.getPostCount());
        subreddit.setIconUrl(entity.getIconUrl());

        return subreddit;
    }

    public SubredditEntity modelToEntity(Subreddit model) {
        if (model == null) return null;

        SubredditEntity entity = new SubredditEntity();
        entity.setSubredditId(model.getId());
        entity.setName(model.getName());

        AccountEntity account = accountRepository.findById(model.getAccount().getAccountId())
                .orElse(null);
        entity.setAccount(account);

        entity.setDisplayName(model.getDisplayName());
        entity.setDescription(model.getDescription());
        entity.setDeleted(model.isDeleted());
        entity.setMemberCount(model.getMemberCount());
        entity.setPostCount(model.getPostCount());
        entity.setIconUrl(model.getIconUrl());
        entity.setCreatedAt(model.getCreatedAt());

        return entity;
    }
}
