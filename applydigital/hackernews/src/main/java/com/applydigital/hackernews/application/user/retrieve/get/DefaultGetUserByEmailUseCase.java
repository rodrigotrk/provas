package com.applydigital.hackernews.application.user.retrieve.get;

import com.applydigital.hackernews.domain.data.user.User;
import com.applydigital.hackernews.domain.data.user.UserGateway;
import com.applydigital.hackernews.domain.exceptions.NotFoundException;

import java.util.function.Supplier;

public class DefaultGetUserByEmailUseCase extends GetUserByEmailUseCase{

    private final UserGateway userGateway;

    public DefaultGetUserByEmailUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public UserOutput execute(final String email) {
        return this.userGateway.findUserByEmail(email)
                .map(UserOutput::from)
                .orElseThrow(notFound(email));
    }

    private Supplier<NotFoundException> notFound(final String email) {
        return () -> NotFoundException.with(User.class, "email", email);
    }
}
