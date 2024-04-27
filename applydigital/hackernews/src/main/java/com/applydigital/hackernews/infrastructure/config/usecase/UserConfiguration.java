package com.applydigital.hackernews.infrastructure.config.usecase;

import com.applydigital.hackernews.application.user.create.CreateUserUseCase;
import com.applydigital.hackernews.application.user.create.DefaultCreateUserUseCase;
import com.applydigital.hackernews.application.user.retrieve.get.DefaultGetUserByEmailUseCase;
import com.applydigital.hackernews.application.user.retrieve.get.GetUserByEmailUseCase;
import com.applydigital.hackernews.domain.data.user.UserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    private final UserGateway userGateway;

    public UserConfiguration(final UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Bean
    public GetUserByEmailUseCase getUserByEmailUseCase(){
        return new DefaultGetUserByEmailUseCase(userGateway);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(){
        return new DefaultCreateUserUseCase(userGateway);
    }


}
