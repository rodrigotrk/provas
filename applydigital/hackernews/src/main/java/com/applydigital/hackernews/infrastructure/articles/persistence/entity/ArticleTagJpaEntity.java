package com.applydigital.hackernews.infrastructure.articles.persistence.entity;

import com.applydigital.hackernews.domain.data.tag.Tag;
import com.applydigital.hackernews.domain.data.tag.TagID;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "article_tag")
public class ArticleTagJpaEntity {

    @EmbeddedId
    private ArticleTagID id;

    @ManyToOne
    @MapsId("articleId")
    private ArticleJpaEntity article;

    @ManyToOne
    @MapsId("tagId")
    private TagJpaEntity tag;

    public ArticleTagJpaEntity() {}

    private ArticleTagJpaEntity(final ArticleJpaEntity article, final TagJpaEntity tag){
        this.article = article;
        this.tag = tag;
        this.id = ArticleTagID.from(article.getId(), tag.getId());
    }

    public static ArticleTagJpaEntity from(final ArticleJpaEntity article, final TagJpaEntity tag){
        return new ArticleTagJpaEntity(article, tag);
    }

    public ArticleTagID getId() {
        return id;
    }

    public ArticleTagJpaEntity setId(ArticleTagID id) {
        this.id = id;
        return this;
    }

    public ArticleJpaEntity getArticle() {
        return article;
    }

    public ArticleTagJpaEntity setArticle(ArticleJpaEntity article) {
        this.article = article;
        return this;
    }

    public TagJpaEntity getTag() {
        return tag;
    }

    public ArticleTagJpaEntity setTag(TagJpaEntity tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ArticleTagJpaEntity that = (ArticleTagJpaEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
