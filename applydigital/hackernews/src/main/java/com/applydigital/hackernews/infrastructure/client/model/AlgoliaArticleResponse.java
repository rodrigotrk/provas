package com.applydigital.hackernews.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public record  AlgoliaArticleResponse (
        @JsonProperty("hits") List<AlgoliaArticleItemsResponse> hits
) {}
