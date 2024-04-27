package com.applydigital.hackernews.application.article.retrieve.list;

import com.applydigital.hackernews.application.UseCase;
import com.applydigital.hackernews.domain.pagination.Pagination;
import com.applydigital.hackernews.domain.pagination.SearchQuery;

public abstract class ListArticleUseCase extends UseCase<SearchQuery, Pagination<ArticleListOutput>> {
}
