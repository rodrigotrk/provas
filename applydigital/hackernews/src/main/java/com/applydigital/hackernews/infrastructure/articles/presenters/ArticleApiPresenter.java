package com.applydigital.hackernews.infrastructure.articles.presenters;

import com.applydigital.hackernews.application.article.retrieve.list.ArticleListOutput;
import com.applydigital.hackernews.domain.data.tag.Tag;
import com.applydigital.hackernews.domain.util.CollectionUtils;
import com.applydigital.hackernews.infrastructure.articles.model.ArticleResponse;

public interface ArticleApiPresenter {

    static ArticleResponse present(final ArticleListOutput output){
        return  new ArticleResponse(
                output.objectId(),
                output.author(),
                output.commentText(),
                output.storyTitle(),
                output.storyUrl(),
                output.createdAt(),
                output.tags(),
                output.parentId(),
                output.storyId(),
                output.updatedAt()
        );
    }
}
