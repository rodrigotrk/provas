package com.applydigital.hackernews.infrastructure.articles.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Set;

public record CreateArticleRequest(
        @JsonProperty("objectID") String objectID,
        @JsonProperty("author") String author,
        @JsonProperty("comment_text") String commentText,
        @JsonProperty("story_title") String storyTitle,
        @JsonProperty("story_url") String storyUrl,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("_tags") Set<String> tags,
        @JsonProperty("parent_id") Long parentId,
        @JsonProperty("story_id") Long storyId,
        @JsonProperty("updated_at") Instant updatedAt

) {
    public static CreateArticleRequest with(
            final String objectId,
            final String author,
            final String commentText,
            final String storyTitle,
            final String storyUrl,
            final Instant createdAt,
            final Set<String> tags,
            final Long parentId,
            final Long storyId,
            final Instant updatedAt

    ) {
        return new CreateArticleRequest(objectId, author, commentText, storyTitle, storyUrl, createdAt, tags, parentId, storyId, updatedAt);
    }
}
