package com.applydigital.hackernews.application.user.create;

import com.applydigital.hackernews.domain.data.user.User;
import com.applydigital.hackernews.domain.data.user.UserGateway;
import com.applydigital.hackernews.domain.exceptions.NotificationException;
import com.applydigital.hackernews.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultCreateUserUseCase extends CreateUserUseCase{

    private final UserGateway userGateway;

    public DefaultCreateUserUseCase(UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public CreateUserOutput execute(final CreateUserCommand createUserCommand) {
        final var notification = Notification.create();

        final var user = notification.validate(() -> User.newUser(
                createUserCommand.name(),
                createUserCommand.email(),
                createUserCommand.password()
                ));

        if(notification.hasError()){
            notify(notification);
        }

        return CreateUserOutput.from(this.userGateway.create(user));
    }

    private void notify(Notification notification) {
        throw new NotificationException("Could not create Aggregate User", notification);
    }
}
