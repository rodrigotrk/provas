package com.applydigital.hackernews.infrastructure.articles.persistence.entity;

import com.applydigital.hackernews.domain.data.tag.Tag;
import com.applydigital.hackernews.domain.data.tag.TagID;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "tag")
public class TagJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "tag", nullable = false)
    private String tag;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @OneToMany(mappedBy = "tag", cascade = ALL, fetch = EAGER, orphanRemoval = true)
    private Set<ArticleTagJpaEntity> articles;

    public TagJpaEntity() {}

    private TagJpaEntity(String tag) {
        this.tag = tag;
    }

    public TagJpaEntity(String id, String tag, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.tag = tag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.articles = new HashSet<>();
    }

    public static TagJpaEntity from(final Tag tag){
        return  new TagJpaEntity(
                tag.getId().getValue(),
                tag.getTag(),
                tag.getCreatedAt(),
                tag.getUpdatedAt()
        );
    }

    public Tag toAggregate(){
        return Tag.with(
                TagID.from(getId()),
                getTag(),
                getCreatedAt(),
                getUpdatedAt()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public TagJpaEntity setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public TagJpaEntity setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Set<ArticleTagJpaEntity> getArticles() {
        return articles;
    }
    public TagJpaEntity setArticles(Set<ArticleTagJpaEntity> articles) {
        this.articles = articles;
        return this;
    }
}
