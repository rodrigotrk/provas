package com.applydigital.hackernews.infrastructure.articles.persistence.entity;

import com.applydigital.hackernews.domain.data.article.Article;
import com.applydigital.hackernews.domain.data.article.ArticleID;
import com.applydigital.hackernews.domain.data.tag.Tag;
import com.applydigital.hackernews.domain.data.tag.TagID;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "article")
public class ArticleJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "object_id")
    private String objectId;

    private String author;

    @Lob
    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "story_title")
    private String storyTitle;

    @Column(name = "story_url")
    private String storyUrl;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "story_id")
    private Long storyId;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "month")
    private String month;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "article", cascade = ALL, fetch = EAGER, orphanRemoval = true)
    private Set<ArticleTagJpaEntity> tags;


    public ArticleJpaEntity() {}

    private ArticleJpaEntity(String id) {
        this.id = id;
    }

    private ArticleJpaEntity(
            final String id,
            final String objectId,
            final String author,
            final String commentText,
            final String storyTitle,
            final String storyUrl,
            final Instant createdAt,
            final Long parentId,
            final Long storyId,
            final Instant updatedAt,
            final Instant deletedAt,
            final String month,
            final boolean active
    ) {
        this.id = id;
        this.objectId = objectId;
        this.author = author;
        this.commentText = commentText;
        this.storyTitle = storyTitle;
        this.storyUrl = storyUrl;
        this.createdAt = createdAt;
        this.parentId = parentId;
        this.storyId = storyId;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.month = month;
        this.active = active;
        this.tags = new HashSet<>();
    }

    public static ArticleJpaEntity from(final Article article){
        final var aArticle = new ArticleJpaEntity(
                article.getId().getValue(),
                article.getObjectId(),
                article.getAuthor(),
                article.getCommentText(),
                article.getStoryTitle(),
                article.getStoryUrl(),
                article.getCreatedAt(),
                article.getParentId(),
                article.getStoryId(),
                article.getUpdatedAt(),
                article.getDeletedAt(),
                article.getMonth(),
                article.isActive()
        );

        article.getTags().forEach(aArticle::addTag);

        return aArticle;
    }

    public static ArticleJpaEntity from(final ArticleID articleID){
        return new ArticleJpaEntity(articleID.getValue());
    }

    public Article toAggregate(){
        return Article.with(
                ArticleID.from(getId()),
                getObjectId(),
                getAuthor(),
                getCommentText(),
                getStoryTitle(),
                getStoryUrl(),
                getParentId(),
                getStoryId(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt(),
                getMonth(),
                isActive(),
                getTagsDomain()
        );
    }

    public Set<Tag> getTagsDomain() {
        return getTags().stream()
                .map(ArticleTagJpaEntity::getTag)
                .map(TagJpaEntity::toAggregate)
                .collect(Collectors.toSet());
    }

    private void addTag(final Tag tag) {
        TagJpaEntity tagEntity = TagJpaEntity.from(tag);
        ArticleTagJpaEntity articleTag = ArticleTagJpaEntity.from(this, tagEntity);
        this.tags.add(articleTag);
        tagEntity.getArticles().add(articleTag);
    }

    public String getId() {
        return id;
    }

    public ArticleJpaEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getObjectId() {
        return objectId;
    }

    public ArticleJpaEntity setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleJpaEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public ArticleJpaEntity setCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public ArticleJpaEntity setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
        return this;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public ArticleJpaEntity setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ArticleJpaEntity setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public ArticleJpaEntity setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public Long getStoryId() {
        return storyId;
    }

    public ArticleJpaEntity setStoryId(Long storyId) {
        this.storyId = storyId;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public ArticleJpaEntity setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public ArticleJpaEntity setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public String getMonth() {
        return month;
    }

    public ArticleJpaEntity setMonth(String month) {
        this.month = month;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public ArticleJpaEntity setActive(boolean active) {
        this.active = active;
        return this;
    }

    public Set<ArticleTagJpaEntity> getTags() {
        return tags;
    }

    public ArticleJpaEntity setTags(Set<ArticleTagJpaEntity> tags) {
        this.tags = tags;
        return this;
    }
}
