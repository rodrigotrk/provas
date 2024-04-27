package com.applydigital.hackernews.application.article.imports;

import com.applydigital.hackernews.domain.data.article.Article;

public record ImportArticleOutput(
        String id
) {
    public static ImportArticleOutput from(final String id){
        return new ImportArticleOutput(id);
    }

    public static ImportArticleOutput from (final Article article){
        return new ImportArticleOutput(article.getId().getValue());
    }
}
