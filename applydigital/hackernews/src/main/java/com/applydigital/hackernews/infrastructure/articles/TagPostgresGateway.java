package com.applydigital.hackernews.infrastructure.articles;

import com.applydigital.hackernews.domain.data.tag.Tag;
import com.applydigital.hackernews.domain.data.tag.TagGateway;
import com.applydigital.hackernews.domain.data.tag.TagID;
import com.applydigital.hackernews.domain.pagination.Pagination;
import com.applydigital.hackernews.domain.pagination.SearchQuery;
import com.applydigital.hackernews.infrastructure.articles.persistence.entity.TagJpaEntity;
import com.applydigital.hackernews.infrastructure.articles.persistence.repository.TagRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class TagPostgresGateway implements TagGateway {

    private final TagRepository repository;

    public TagPostgresGateway(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tag save(Tag tag) {
        return this.repository.save(TagJpaEntity.from(tag)).toAggregate();
    }

    @Override
    public Tag update(Tag tag) {
        return null;
    }

    @Override
    public void deleteById(Tag id) {

    }

    @Override
    public Optional<Tag> findById(Tag id) {
        return Optional.empty();
    }

    @Override
    public Pagination<Tag> findAll(SearchQuery aQuery) {
        // Paginação
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        // Busca dinamica pelo criterio terms (name ou description)
        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =  this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(TagJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<TagID> existsByIds(Iterable<TagID> tagIds) {
        final var ids = StreamSupport.stream(tagIds.spliterator(), false)
                .map(TagID::getValue)
                .toList();
        return this.repository.existsByIds(ids).stream()
                .map(TagID::from)
                .toList();
    }

    private Specification<TagJpaEntity> assembleSpecification(final String searchTerms) {
        return (root, query, cb) -> {

            String[] terms = searchTerms.split(",");

            Predicate[] predicates = Arrays.stream(terms)
                    .map(term -> cb.like(root.get("tag"), "%" + term.trim() + "%"))
                    .toArray(Predicate[]::new);

            return cb.or(predicates);
        };
    }
}
