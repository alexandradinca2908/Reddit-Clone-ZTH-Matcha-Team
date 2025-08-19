package org.matcha.springbackend.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncCacheService {
    private final CacheService cacheService;

    public AsyncCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Async
    public void rebuildPostsCache() {
        cacheService.getAllPostsFromDb();
    }
}
