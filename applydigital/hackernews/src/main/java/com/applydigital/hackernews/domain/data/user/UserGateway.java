package com.applydigital.hackernews.domain.data.user;

import com.applydigital.hackernews.domain.data.tag.Tag;
import com.applydigital.hackernews.domain.data.tag.TagID;
import com.applydigital.hackernews.domain.pagination.Pagination;
import com.applydigital.hackernews.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface UserGateway {

    User create (User user);
    Optional<User> findUserByEmail(String email);
}
