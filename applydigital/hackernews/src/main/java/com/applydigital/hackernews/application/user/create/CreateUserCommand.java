package com.applydigital.hackernews.application.user.create;

public record CreateUserCommand(
        String name,
        String email,
        String password
) {

    public static CreateUserCommand with(
        final String name,
        final String email,
        final String password
    ){
        return new CreateUserCommand(name, email, password);
    }
}
