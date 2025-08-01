package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.subreddit.SubredditDto;
import org.matcha.springbackend.model.Subreddit;
import org.springframework.stereotype.Component;
import org.matcha.springbackend.entities.SubredditEntity;

@Component
public class SubredditMapper {

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

    // Converts SubredditEntity to Subreddit model
    public Subreddit entityToModel(org.matcha.springbackend.entities.SubredditEntity entity) {
        if (entity == null) return null;
        Subreddit subreddit = new Subreddit();
        //subreddit.setId(entity.getSubredditId());
        subreddit.setName(entity.getName());
        subreddit.setDisplayName(entity.getName()); // Assuming displayName is same as name
        subreddit.setDescription(entity.getDescription());
        subreddit.setDeleted(entity.isDeleted());
        subreddit.setCreatedAt(entity.getCreatedAt());
        // memberCount, postCount, iconUrl are not present in entity, set as null
        subreddit.setMemberCount(null);
        subreddit.setPostCount(null);
        subreddit.setIconUrl(null);
        // Account mapping can be added if needed
        return subreddit;
    }

    public SubredditEntity modelToEntity(Subreddit model) {
        if (model == null) return null;
        SubredditEntity entity = new SubredditEntity();
        // entity.setSubredditId(model.getId());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setDeleted(model.isDeleted());
        entity.setCreatedAt(model.getCreatedAt());
        // Alte câmpuri (memberCount, postCount, iconUrl) nu există în entity
        return entity;
    }
}
