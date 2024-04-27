package com.applydigital.hackernews.infrastructure.api.controller;

import com.applydigital.hackernews.application.user.create.CreateUserCommand;
import com.applydigital.hackernews.application.user.create.CreateUserUseCase;
import com.applydigital.hackernews.infrastructure.api.UserAPI;
import com.applydigital.hackernews.infrastructure.security.model.AuthenticateRequest;
import com.applydigital.hackernews.infrastructure.users.model.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserAPI {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @Override
    public ResponseEntity<?> createUser(CreateUserRequest input) {
        final var command = CreateUserCommand.with(input.name(), input.email(), input.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createUserUseCase.execute(command));
    }

}
