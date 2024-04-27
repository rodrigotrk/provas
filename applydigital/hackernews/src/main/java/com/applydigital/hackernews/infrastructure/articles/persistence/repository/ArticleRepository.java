package com.applydigital.hackernews.infrastructure.articles.persistence.repository;

import com.applydigital.hackernews.domain.data.article.Article;
import com.applydigital.hackernews.infrastructure.articles.persistence.entity.ArticleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleJpaEntity, String> , JpaSpecificationExecutor<ArticleJpaEntity> {

    Page<ArticleJpaEntity> findAll(Specification<ArticleJpaEntity> whereClause, Pageable page);

    Optional<ArticleJpaEntity> findByObjectId(String objectId);

    @Query(value = "select a.objectId from ArticleJpaEntity a where a.objectId in :objectIds")
    List<String> existsByObjectIds(@Param("objectIds") List<String> objectIds);
}
