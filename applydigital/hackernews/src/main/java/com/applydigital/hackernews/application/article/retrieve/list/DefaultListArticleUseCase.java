package com.applydigital.hackernews.application.article.retrieve.list;


import com.applydigital.hackernews.domain.data.article.ArticleGateway;
import com.applydigital.hackernews.domain.exceptions.NotificationException;
import com.applydigital.hackernews.domain.pagination.Pagination;
import com.applydigital.hackernews.domain.pagination.SearchQuery;
import com.applydigital.hackernews.domain.validation.Error;
import com.applydigital.hackernews.domain.validation.ValidationHandler;
import com.applydigital.hackernews.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultListArticleUseCase extends  ListArticleUseCase{

    private static final int MAX_PER_PAGE = 5;

    private final ArticleGateway articleGateway;

    public DefaultListArticleUseCase(ArticleGateway articleGateway) {
        this.articleGateway = Objects.requireNonNull(articleGateway);
    }

    @Override
    public Pagination<ArticleListOutput> execute(final SearchQuery query){

        final var notification = Notification.create();
        notification.append(validateMaxResultsPerPage(query));

        if (notification.hasError()) {
            throw new NotificationException("Could not find Articles", notification);
        }

        return this.articleGateway.findAll(query)
                .map(ArticleListOutput::from);
    }

    private ValidationHandler validateMaxResultsPerPage(final SearchQuery query){
        final var notification = Notification.create();
        if(query.perPage() > MAX_PER_PAGE){
            notification.append(new Error("'perPage' must be between 0 and 5 results per page"));
        }
        return notification;
    }

}
