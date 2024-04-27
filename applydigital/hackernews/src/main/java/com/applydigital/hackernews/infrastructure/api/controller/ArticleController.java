package com.applydigital.hackernews.infrastructure.api.controller;

import com.applydigital.hackernews.application.article.delete.DeleteArticleUseCase;
import com.applydigital.hackernews.application.article.imports.ImportArticleCommand;
import com.applydigital.hackernews.application.article.imports.ImportArticleUseCase;
import com.applydigital.hackernews.application.article.retrieve.list.ListArticleUseCase;
import com.applydigital.hackernews.domain.pagination.Pagination;
import com.applydigital.hackernews.domain.pagination.SearchQuery;
import com.applydigital.hackernews.infrastructure.api.ArticleAPI;
import com.applydigital.hackernews.infrastructure.articles.model.ArticleResponse;
import com.applydigital.hackernews.infrastructure.articles.presenters.ArticleApiPresenter;
import com.applydigital.hackernews.infrastructure.client.AlgoliaClientService;
import com.applydigital.hackernews.infrastructure.client.model.AlgoliaArticleItemsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArticleController implements ArticleAPI {

    private final ImportArticleUseCase importArticleUseCase;
    private final ListArticleUseCase listArticleUseCase;
    private final AlgoliaClientService algoliaClientService;
    private final DeleteArticleUseCase deleteArticleUseCase;

    public ArticleController(final ImportArticleUseCase importArticleUseCase,
                             final ListArticleUseCase listArticleUseCase,
                             final AlgoliaClientService algoliaClientService,
                             DeleteArticleUseCase deleteArticleUseCase) {
        this.importArticleUseCase = importArticleUseCase;
        this.listArticleUseCase = listArticleUseCase;
        this.algoliaClientService = algoliaClientService;
        this.deleteArticleUseCase = deleteArticleUseCase;
    }

    @Override
    public ResponseEntity<?> importArticles() {
        List<AlgoliaArticleItemsResponse> response =  this.algoliaClientService.findArticles();

        if(response != null && !response.isEmpty()){
            List<ImportArticleCommand> commands = response.stream().map(request ->
                    ImportArticleCommand.with(
                            request.objectID(),
                            request.author(),
                            request.commentText(),
                            request.storyTitle(),
                            request.storyUrl(),
                            request.parentId(),
                            request.storyId(),
                            request.createdAt(),
                            request.updatedAt(),
                            null,
                            true,
                            request.tags())).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.CREATED).body(importArticleUseCase.execute(commands));
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @Override
    public Pagination<ArticleResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction) {
        return this.listArticleUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(ArticleApiPresenter::present);
    }

    @Override
    public void deleteById(final String objectId) {
        this.deleteArticleUseCase.execute(objectId);
    }


}
