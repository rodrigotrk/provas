package com.applydigital.hackernews.application.user.retrieve.get;

import com.applydigital.hackernews.domain.data.user.User;

import java.time.Instant;

public record UserOutput(
        String id,
        String name,
        String email,
        String password,
        Instant createdAt
) {
    public static UserOutput from (final User user){
        return new UserOutput(
                user.getId().getValue(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt()
        );
    }
}
