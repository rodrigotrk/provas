package com.applydigital.hackernews.infrastructure.users.persistence.entity;

import com.applydigital.hackernews.domain.data.user.User;
import com.applydigital.hackernews.domain.data.user.UserID;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "auth_user")
public class UserJpaEntity {

    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "create_at")
    private Instant createdAt;

    public UserJpaEntity() { }

    private UserJpaEntity(
            final String id,
            final String name,
            final String email,
            final String password,
            final Instant createdAt
    ){
      this.id = id;
      this.name = name;
      this.email = email;
      this.password = password;
      this.createdAt = createdAt;
    }

    public static UserJpaEntity from(final User user){
        return new UserJpaEntity(
                user.getId().getValue(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt());
    }

    public User toAggregate(){
        return User.with(
                UserID.from(getId()),
                getName(),
                getEmail(),
                getPassword(),
                getCreatedAt()
        );
    }

    public String getId() {
        return id;
    }

    public UserJpaEntity setId(String id) {
        this.id = id;
        return this;
    }



    public String getName() {
        return name;
    }

    public UserJpaEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserJpaEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserJpaEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserJpaEntity setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
