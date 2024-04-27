package com.applydigital.hackernews.application.article.retrieve.list;

import com.applydigital.hackernews.domain.data.Identifier;
import com.applydigital.hackernews.domain.data.article.Article;
import com.applydigital.hackernews.domain.data.tag.Tag;
import com.applydigital.hackernews.domain.util.CollectionUtils;

import java.time.Instant;
import java.util.Set;

public record ArticleListOutput(
        String objectId,
        String author,
        String commentText,
        String storyTitle,
        String storyUrl,
        Long parentId,
        Long storyId,
        Instant createdAt,
        Instant updatedAt,
        Set<String> tags
) {

    public static ArticleListOutput from(final Article article){
        return new ArticleListOutput(
                article.getObjectId(),
                article.getAuthor(),
                article.getCommentText(),
                article.getStoryTitle(),
                article.getStoryUrl(),
                article.getParentId(),
                article.getStoryId(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                CollectionUtils.mapTo(article.getTags(), Tag::getTag)
        );
    }
}
