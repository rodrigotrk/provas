package com.applydigital.hackernews.application.article.imports;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record ImportArticleCommand(
        String objectId,
        String author,
        String commentText,
        String storyTitle,
        String storyUrl,
        Long parentId,
        Long storyId,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt,
        boolean isActive,
        Set<String> tags
) {
    public static ImportArticleCommand with(
            final String objectId,
            final String author,
            final String commentText,
            final String storyTitle,
            final String storyUrl,
            final Long parentId,
            final Long storyId,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt,
            final Boolean isActive,
            final Set<String> tags
    ) {
        return new ImportArticleCommand(objectId, author, commentText, storyTitle, storyUrl, parentId, storyId, createdAt, updatedAt, deletedAt, isActive != null ? isActive : true , tags);
    }
}
