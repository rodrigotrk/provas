package com.applydigital.hackernews.application.user.create;

import com.applydigital.hackernews.domain.data.user.User;

public record CreateUserOutput (
        String id
) {
    public static CreateUserOutput from(final String id){
        return new CreateUserOutput(id);
    }

    public static CreateUserOutput from (final User user){
        return new CreateUserOutput(user.getId().getValue());
    }
}
