package com.applydigital.hackernews.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record AlgoliaArticleItemsResponse (
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
) {}
