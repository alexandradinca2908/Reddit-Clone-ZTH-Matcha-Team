package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.subreddit.SubredditDTO;
import org.matcha.springbackend.model.Subreddit;
import org.springframework.stereotype.Component;

@Component
public class SubredditMapper {

    public Subreddit entityToModel(SubredditDTO dto) {
        if (dto == null) return null;
        Subreddit subreddit = new Subreddit(
                java.util.UUID.fromString(dto.id()),
                dto.name(),
                dto.displayName(),
                dto.description(),
                false, // isDeleted not present in DTO, default to false
                dto.memberCount(),
                dto.postCount(),
                dto.iconUrl(),
                java.time.OffsetDateTime.parse(dto.createdAt())
        );
        return subreddit;
    }

    public SubredditDTO modelToDTO(Subreddit model) {
        String id = model.getId().toString();
        String name = model.getName();
        String displayName = model.getDisplayName();
        String description = model.getDescription();
        Integer memberCount = model.getMemberCount();
        Integer postCount = model.getPostCount();
        String iconURL = model.getIconUrl();
        String createdAt = model.getCreatedAt().toString();

        return new SubredditDTO(id, name, displayName, description, memberCount,
                postCount, iconURL, createdAt);
    }

    // Converts SubredditEntity to Subreddit model
    public Subreddit entityToModel(org.matcha.springbackend.entities.SubredditEntity entity) {
        if (entity == null) return null;
        Subreddit subreddit = new Subreddit();
        subreddit.setId(entity.getSubredditId());
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
}
