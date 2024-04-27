package com.applydigital.hackernews.infrastructure.config.usecase;

import com.applydigital.hackernews.application.article.delete.DefaultDeleteArticleUseCase;
import com.applydigital.hackernews.application.article.delete.DeleteArticleUseCase;
import com.applydigital.hackernews.application.article.imports.DefaultImportArticleUseCase;
import com.applydigital.hackernews.application.article.imports.ImportArticleUseCase;
import com.applydigital.hackernews.application.article.retrieve.list.DefaultListArticleUseCase;
import com.applydigital.hackernews.application.article.retrieve.list.ListArticleUseCase;
import com.applydigital.hackernews.domain.data.article.ArticleGateway;
import com.applydigital.hackernews.domain.data.tag.TagGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ArticleConfiguration {

    private final ArticleGateway articleGateway;
    private final TagGateway tagGateway;

    public ArticleConfiguration(final ArticleGateway articleGateway, final TagGateway tagGateway) {
        this.articleGateway = articleGateway;
        this.tagGateway = tagGateway;
    }

    @Bean
    public ImportArticleUseCase importArticleUseCase(){
        return new DefaultImportArticleUseCase(articleGateway, tagGateway);
    }

    @Bean
    public ListArticleUseCase listArticleUseCase(){
        return new DefaultListArticleUseCase(articleGateway);
    }

    @Bean
    public DeleteArticleUseCase deleteArticleUseCase(){
        return new DefaultDeleteArticleUseCase(articleGateway);
    }
}
