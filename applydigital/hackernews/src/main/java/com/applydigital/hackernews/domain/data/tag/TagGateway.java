package com.applydigital.hackernews.domain.data.tag;

import com.applydigital.hackernews.domain.pagination.Pagination;
import com.applydigital.hackernews.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface TagGateway {

    Tag save(Tag tag);

    Tag update(Tag tag);

    public void deleteById(Tag id);

    Optional<Tag> findById(Tag id);

    Pagination<Tag> findAll(SearchQuery aQuery);

    List<TagID> existsByIds(Iterable<TagID> ids);
}
