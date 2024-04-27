package com.applydigital.hackernews.infrastructure.articles.persistence.repository;

import com.applydigital.hackernews.infrastructure.articles.persistence.entity.TagJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TagRepository extends JpaRepository<TagJpaEntity, String> {

    Page<TagJpaEntity> findAll(Specification<TagJpaEntity> whereClause, Pageable page);

    @Query(value = "select g.id from TagJpaEntity g where g.id in :ids")
    List<String> existsByIds(@Param("ids") List<String> ids);
}
