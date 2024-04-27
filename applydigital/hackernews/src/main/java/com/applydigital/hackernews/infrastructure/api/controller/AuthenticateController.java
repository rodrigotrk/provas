package com.applydigital.hackernews.infrastructure.api.controller;

import com.applydigital.hackernews.application.user.retrieve.get.GetUserByEmailUseCase;
import com.applydigital.hackernews.domain.data.user.User;
import com.applydigital.hackernews.domain.data.user.UserID;
import com.applydigital.hackernews.infrastructure.api.AuthenticateAPI;
import com.applydigital.hackernews.infrastructure.security.JwtService;
import com.applydigital.hackernews.infrastructure.security.model.AuthenticateResponse;
import com.applydigital.hackernews.infrastructure.security.model.AuthenticateRequest;
import com.applydigital.hackernews.infrastructure.users.persistence.entity.UserJpaEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController implements AuthenticateAPI {

    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticateController(GetUserByEmailUseCase getUserByEmailUseCase, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.getUserByEmailUseCase = getUserByEmailUseCase;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticateRequest input) {
        var user = getUserByEmailUseCase.execute(input.email());

        if(user == null) {
            return null;
        }

        boolean matches = passwordEncoder.matches(input.password(), user.password());

        if(matches){
            var userToGetAccessToken =  User.with(UserID.from(user.id()), user.name(), user.email(), user.password(), user.createdAt());
            var userJpa = UserJpaEntity.from(userToGetAccessToken);
            AuthenticateResponse token = jwtService.generateToken(userJpa);
            return ResponseEntity.ok(token);
        }

        return null;
    }


    private String encoderPassword(String password){
        return passwordEncoder.encode(password);
    }
}
