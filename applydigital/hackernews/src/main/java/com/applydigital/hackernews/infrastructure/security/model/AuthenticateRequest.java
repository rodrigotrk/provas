package com.applydigital.hackernews.infrastructure.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticateRequest(
             @JsonProperty("email") String email,
             @JsonProperty("password") String password
) {
    public static AuthenticateRequest with(
            final String email,
            final String password
    ) {
        return new AuthenticateRequest(email, password);
    }
}
