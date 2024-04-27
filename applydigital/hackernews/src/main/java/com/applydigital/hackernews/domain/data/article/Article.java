package com.applydigital.hackernews.domain.data.article;


import com.applydigital.hackernews.domain.data.AggregateRoot;
import com.applydigital.hackernews.domain.data.tag.Tag;
import com.applydigital.hackernews.domain.exceptions.NotificationException;
import com.applydigital.hackernews.domain.util.InstantUtils;
import com.applydigital.hackernews.domain.validation.ValidationHandler;
import com.applydigital.hackernews.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.*;

public class Article extends AggregateRoot<ArticleID> {

    private String objectId;

    private String author;

    private String commentText;

    private String storyTitle;

    private String storyUrl;

    private Long parentId;

    private Long storyId;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    private String month;

    private boolean active;

    private Set<Tag> tags;

    protected Article(
            ArticleID articleID,
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
            String month,
            boolean isActive,
            Set<Tag> tags
    ) {
        super(articleID);
        this.objectId = objectId;
        this.author = author;
        this.commentText = commentText;
        this.storyTitle = storyTitle;
        this.storyUrl = storyUrl;
        this.parentId = parentId;
        this.storyId = storyId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.month = month;
        this.active = isActive;
        this.tags = tags;
        selfValidate();
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new ArticleValidator(this, handler).validate();
    }

    public static Article newArticle(
            final String objectId,
            final String author,
            final String commentText,
            final String storyTitle,
            final String storyUrl,
            final Long parentId,
            final Long storyId,
            final Instant createdAt,
            final Instant updatedAt,
            final boolean isActive,
            final Set<Tag> tags
    ){
        final var id = ArticleID.unique();
        var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        final var month = InstantUtils.getMonth(createdAt);
        return new Article(
                id,
                objectId,
                author,
                commentText,
                storyTitle,
                storyUrl,
                parentId,
                storyId,
                createdAt,
                updatedAt,
                deletedAt,
                month,
                isActive,
                tags
        );
    }

    public static Article with(
            final ArticleID articleID,
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
            final String month,
            final boolean isActive,
            final Set<Tag> tags
    ){
        return new Article(
                articleID,
                objectId,
                author,
                commentText,
                storyTitle,
                storyUrl,
                parentId,
                storyId,
                createdAt,
                updatedAt,
                deletedAt,
                month,
                isActive,
                tags
        );
    }

    public static Article with(final Article article){
        return with(
                article.getId(),
                article.getObjectId(),
                article.getAuthor(),
                article.getCommentText(),
                article.getStoryTitle(),
                article.getStoryUrl(),
                article.getParentId(),
                article.getStoryId(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.getDeletedAt(),
                article.getMonth(),
                article.isActive(),
                new HashSet<>(article.getTags())
        );
    }

   public Article addTags(final Set<Tag> tags){
        if(tags == null || tags.isEmpty()){
            return this;
        }
        this.tags.addAll(tags);
        return this;
    }

    public Article deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = InstantUtils.now();
        }

        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to create a Aggregate Article", notification);
        }
    }

    public String getObjectId() {
        return objectId;
    }

    public String getAuthor() {
        return author;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public Long getParentId() {
        return parentId;
    }

    public Long getStoryId() {
        return storyId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public String getMonth() {
        return month;
    }

    public boolean isActive() {
        return active;
    }

    public Set<Tag> getTags() {
        return tags != null ? Collections.unmodifiableSet(tags) : Collections.emptySet();
    }

    private void setTags(final Set<Tag> tags){
        this.tags = tags != null ? new HashSet<>(tags) : Collections.emptySet();
    }
}
