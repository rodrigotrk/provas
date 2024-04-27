package com.applydigital.hackernews.infrastructure.users;

import com.applydigital.hackernews.domain.data.user.User;
import com.applydigital.hackernews.domain.data.user.UserGateway;
import com.applydigital.hackernews.infrastructure.users.persistence.entity.UserJpaEntity;
import com.applydigital.hackernews.infrastructure.users.persistence.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPostgreGateway implements UserGateway {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserPostgreGateway(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        final var userToSave = User.newUser(user.getName(), user.getEmail(), encoderPassword(user.getPassword()));
        return this.repository.save(UserJpaEntity.from(userToSave)).toAggregate();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return this.repository.findByEmail(email).map(UserJpaEntity::toAggregate);
    }


    private String encoderPassword(String password){
        return passwordEncoder.encode(password);
    }
}
