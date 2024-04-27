package com.applydigital.hackernews.infrastructure.articles.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ArticleTagID implements Serializable {

    @Column(name = "article_id", nullable = false)
    private String articleId;

    @Column(name = "tag_id", nullable = false)
    private String tagId;

    public ArticleTagID() {}

    public ArticleTagID(String articleId, String tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }

    public static ArticleTagID from(final String articleId, final String tagId){
        return new ArticleTagID(articleId, tagId);
    }

    public String getArticleId() {
        return articleId;
    }

    public ArticleTagID setArticleId(String articleId) {
        this.articleId = articleId;
        return this;
    }

    public String getTagId() {
        return tagId;
    }

    public ArticleTagID setTagId(String tagId){
        this.tagId = tagId;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ArticleTagID that = (ArticleTagID) o;
        return Objects.equals(getArticleId(), that.getArticleId()) && Objects.equals(getTagId(), that.getTagId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticleId(), getTagId());
    }
}
