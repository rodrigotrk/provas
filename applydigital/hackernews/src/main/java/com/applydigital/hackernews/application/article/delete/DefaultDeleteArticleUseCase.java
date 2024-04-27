package com.applydigital.hackernews.application.article.delete;

import com.applydigital.hackernews.domain.data.article.Article;
import com.applydigital.hackernews.domain.data.article.ArticleGateway;
import com.applydigital.hackernews.domain.data.article.ArticleID;
import com.applydigital.hackernews.domain.exceptions.NotFoundException;

public class DefaultDeleteArticleUseCase extends DeleteArticleUseCase{

    private final ArticleGateway articleGateway;

    public DefaultDeleteArticleUseCase(ArticleGateway articleGateway) {
        this.articleGateway = articleGateway;
    }

    @Override
    public void execute(String id) {
        Article article = articleGateway.findByObjectId(id).orElseThrow(() -> NotFoundException.with(Article.class, ArticleID.from(id)));
        article.deactivate();
        articleGateway.update(article);
    }
}
