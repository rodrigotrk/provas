package com.applydigital.hackernews.application.article.imports;

import com.applydigital.hackernews.domain.data.article.Article;
import com.applydigital.hackernews.domain.data.article.ArticleGateway;
import com.applydigital.hackernews.domain.data.tag.Tag;
import com.applydigital.hackernews.domain.data.tag.TagGateway;
import com.applydigital.hackernews.domain.data.tag.TagID;
import com.applydigital.hackernews.domain.exceptions.NotificationException;
import com.applydigital.hackernews.domain.pagination.Pagination;
import com.applydigital.hackernews.domain.pagination.SearchQuery;
import com.applydigital.hackernews.domain.validation.Error;
import com.applydigital.hackernews.domain.validation.ValidationHandler;
import com.applydigital.hackernews.domain.validation.handler.Notification;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultImportArticleUseCase extends ImportArticleUseCase {

    private final ArticleGateway articleGateway;
    private final TagGateway tagGateway;

    public DefaultImportArticleUseCase(final ArticleGateway articleGateway, final TagGateway tagGateway) {
        this.articleGateway = Objects.requireNonNull(articleGateway);
        this.tagGateway = Objects.requireNonNull(tagGateway);
    }

    @Override
    public List<ImportArticleOutput> execute(List<ImportArticleCommand> commands) {

        List<String> allObjectIds = commands.stream()
                .map(ImportArticleCommand::objectId)
                .collect(Collectors.toList());

        List<String> existingArticleIds = retrieveExistingArticleObjectIds(allObjectIds);

        List<ImportArticleCommand> filteredCommands = commands.stream()
                .filter(command -> !existingArticleIds.contains(command.objectId()))
                .collect(Collectors.toList());

        if(filteredCommands.isEmpty()){
            return Collections.emptyList();
        }


        List<Article> articlesToCreate = filteredCommands.stream().map(command -> {

            Set<Tag> tags = manageTags(command);

            final var article = Article.newArticle(
                    command.objectId(),
                    command.author(),
                    command.commentText(),
                    command.storyTitle(),
                    command.storyUrl(),
                    command.parentId(),
                    command.storyId(),
                    command.createdAt(),
                    command.updatedAt(),
                    command.isActive(),
                    tags);

            article.addTags(tags);

            return article;
        }).collect(Collectors.toList());

        List<Article> createdArticles = this.articleGateway.create(articlesToCreate);

        return createdArticles.stream()
                .map(ImportArticleOutput::from)
                .collect(Collectors.toList());
    }

   public Set<Tag> manageTags(ImportArticleCommand command) {
        Set<String> tagNames = command.tags();

        SearchQuery query = new SearchQuery(0, tagNames.size(), String.join(",", tagNames), "tag", "asc");
        Pagination<Tag> existingTags = tagGateway.findAll(query);

        Set<Tag> allTags = new HashSet<>(existingTags.items());

        Set<String> existingTagNames = existingTags.items().stream()
                .map(Tag::getTag)
                .collect(Collectors.toSet());

        tagNames.stream()
                .filter(tagName -> !existingTagNames.contains(tagName))
                .forEach(tagName -> {
                    Tag newTag = Tag.newTag(tagName);
                    tagGateway.save(newTag);
                    allTags.add(newTag);
                });

        return allTags;
    }

    private List<String> retrieveExistingArticleObjectIds(final List<String> objectIds) {
        if (objectIds == null || objectIds.isEmpty()) {
            return Collections.emptyList();
        }

       return articleGateway.existsByObjectIds(objectIds);
    }


}
