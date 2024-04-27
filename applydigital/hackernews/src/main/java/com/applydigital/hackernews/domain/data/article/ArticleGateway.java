package com.applydigital.hackernews.domain.data.article;

import com.applydigital.hackernews.domain.pagination.Pagination;
import com.applydigital.hackernews.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface ArticleGateway {

    List<Article> create(List<Article> article);

    Article update(Article aCategory);

    Pagination<Article> findAll(SearchQuery aQuery);

    List<String> existsByObjectIds(Iterable<String> objectIds);

    Optional<Article> findByObjectId(String objectId);
}
