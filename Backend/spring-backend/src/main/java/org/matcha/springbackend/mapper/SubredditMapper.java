package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.subreddit.SubredditDTO;
import org.matcha.springbackend.model.Subreddit;
import org.springframework.stereotype.Component;

@Component
public class SubredditMapper {
    public Subreddit entityToModel(SubredditDTO dto) {
        //  TODO
        return null;
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
}
