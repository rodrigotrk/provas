package com.applydigital.hackernews.domain.data.user;

import com.applydigital.hackernews.domain.data.AggregateRoot;
import com.applydigital.hackernews.domain.exceptions.NotificationException;
import com.applydigital.hackernews.domain.validation.ValidationHandler;
import com.applydigital.hackernews.domain.validation.handler.Notification;

import java.time.Instant;

public class User extends AggregateRoot<UserID> {

    private String name;
    private String email;
    private String password;
    private Instant createdAt;

    protected User(
            UserID userID,
            String name,
            String email,
            String password,
            Instant createdAt
    ){
        super(userID);
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        selfValidate();
    }

    public static User newUser(
            final String name,
            final String email,
            final String password
    ){
        final var id = UserID.unique();
        var now = Instant.now();
        return new User(id, name, email, password, now);
    }

    public static User with(
            final UserID userID,
            final String name,
            final String email,
            final String password,
            final Instant createdAt
    ){
        return new User(
                userID,
                name,
                email,
                password,
                createdAt
        );
    }

    public static User with(final User user){
        return with(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt()
        );
    }

    @Override
    public void validate(ValidationHandler handler) {
        new UserValidator(this, handler).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to create a Aggregate User", notification);
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
