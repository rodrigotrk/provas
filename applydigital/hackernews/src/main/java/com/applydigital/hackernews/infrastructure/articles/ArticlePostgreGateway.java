package com.applydigital.hackernews.infrastructure.articles;

import com.applydigital.hackernews.domain.data.article.Article;
import com.applydigital.hackernews.domain.data.article.ArticleGateway;
import com.applydigital.hackernews.domain.data.article.ArticleID;
import com.applydigital.hackernews.domain.pagination.Pagination;
import com.applydigital.hackernews.domain.pagination.SearchQuery;
import com.applydigital.hackernews.infrastructure.articles.persistence.entity.ArticleJpaEntity;
import com.applydigital.hackernews.infrastructure.articles.persistence.entity.ArticleTagJpaEntity;
import com.applydigital.hackernews.infrastructure.articles.persistence.repository.ArticleRepository;
import com.applydigital.hackernews.infrastructure.articles.persistence.entity.TagJpaEntity;
import com.applydigital.hackernews.infrastructure.util.SqlUtils;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.applydigital.hackernews.infrastructure.util.SpecificationUtils.*;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ArticlePostgreGateway implements ArticleGateway {

    private final ArticleRepository repository;

    public ArticlePostgreGateway(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Article> create(List<Article> articles) {
        return save(articles);
    }

    @Override
    public Article update(Article article) {
        return this.repository.save(ArticleJpaEntity.from(article)).toAggregate();
    }

    @Override
    public Pagination<Article> findAll(SearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(where(isActive()));

        final var pageResult = this.repository.findAll(specifications, page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(ArticleJpaEntity::toAggregate).toList()
        );
    }

        @Override
        public List<String> existsByObjectIds(Iterable<String> objectIds) {
            final var ids = StreamSupport.stream(objectIds.spliterator(), false).toList();
            return this.repository.existsByObjectIds(ids);
        }

    @Override
    public Optional<Article> findByObjectId(String objectId) {
        return this.repository.findByObjectId(objectId)
                .map(ArticleJpaEntity::toAggregate);
    }

    private List<Article> save(final List<Article> articles) {
        List<ArticleJpaEntity> articleEntities = articles.stream()
                .map(ArticleJpaEntity::from)
                .collect(Collectors.toList());

        List<ArticleJpaEntity> savedEntities = repository.saveAll(articleEntities);
        return savedEntities.stream()
                .map(ArticleJpaEntity::toAggregate)
                .collect(Collectors.toList());
    }

    private Specification<ArticleJpaEntity> assembleSpecification(final String terms) {

        String[] splitTerms = terms.split(",");
        Specification<ArticleJpaEntity> spec = where(null);

        for (String term : splitTerms) {
            String trimmedTerm = term.trim();

            if (!trimmedTerm.isEmpty()) {
                spec = combine(term);
            }
        }
        return spec;

    }

    private static Specification<ArticleJpaEntity> combine(String term) {
        return (root, query, cb) -> {
            query.distinct(true);

            Join<ArticleJpaEntity, ArticleTagJpaEntity> articleTagJoin = root.join("tags", JoinType.INNER);
            Join<ArticleTagJpaEntity, TagJpaEntity> tagJoin = articleTagJoin.join("tag", JoinType.INNER);

            Predicate isActive = cb.isTrue(root.get("active"));
            Predicate monthLike = cb.like(cb.upper(root.get("month")), SqlUtils.like(term));
            Predicate titleLike = cb.like(cb.upper(root.get("storyTitle")), SqlUtils.like(term));
            Predicate authorLike = cb.like(cb.upper(root.get("author")), SqlUtils.like(term));
            Predicate tagLike = cb.like(cb.upper(tagJoin.get("tag")), SqlUtils.like(term));

            Predicate like = cb.or(monthLike, titleLike, authorLike, tagLike);

            return cb.and(isActive, like);

        };
    }

}
