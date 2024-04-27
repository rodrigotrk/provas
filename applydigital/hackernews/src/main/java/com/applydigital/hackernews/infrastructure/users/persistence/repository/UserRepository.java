package com.applydigital.hackernews.infrastructure.users.persistence.repository;

import com.applydigital.hackernews.infrastructure.users.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserJpaEntity, String> {
    Optional<UserJpaEntity> findByEmail(String email);
}
