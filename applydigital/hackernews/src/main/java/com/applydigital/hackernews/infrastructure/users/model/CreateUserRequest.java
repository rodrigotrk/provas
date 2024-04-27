package com.applydigital.hackernews.infrastructure.users.model;

import com.applydigital.hackernews.infrastructure.articles.model.CreateArticleRequest;
import com.fasterxml.jackson.annotation.JsonProperty;


public record CreateUserRequest(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
) {
    public static CreateUserRequest with(
            final String name,
            final String email,
            final String password

    ) {
        return new CreateUserRequest(name, email, password);
    }
}
