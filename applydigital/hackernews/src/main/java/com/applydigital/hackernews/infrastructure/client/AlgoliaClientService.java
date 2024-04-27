package com.applydigital.hackernews.infrastructure.client;

import com.applydigital.hackernews.application.article.imports.ImportArticleCommand;
import com.applydigital.hackernews.application.article.imports.ImportArticleUseCase;
import com.applydigital.hackernews.infrastructure.client.model.AlgoliaArticleItemsResponse;
import com.applydigital.hackernews.infrastructure.client.model.AlgoliaArticleResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlgoliaClientService {

    private final RestTemplate restTemplate;
    private final ImportArticleUseCase importArticleUseCase;

    public AlgoliaClientService(final RestTemplate restTemplate, final ImportArticleUseCase importArticleUseCase) {
        this.restTemplate = restTemplate;
        this.importArticleUseCase = importArticleUseCase;
    }

    @Scheduled(fixedRate = 3600000) // 3600000 miliseconds = 1 hour -- 60000 miliseconds = 1 minute
    public void importArticles() {

        List<AlgoliaArticleItemsResponse> response =  findArticles();

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

            importArticleUseCase.execute(commands);
        }

    }

    public List<AlgoliaArticleItemsResponse> findArticles(){
        String url = "https://hn.algolia.com/api/v1/search_by_date?query=java";
        AlgoliaArticleResponse response  = restTemplate.getForObject(url, AlgoliaArticleResponse.class);
        return response != null ? response.hits() : Collections.emptyList();
    }
}
