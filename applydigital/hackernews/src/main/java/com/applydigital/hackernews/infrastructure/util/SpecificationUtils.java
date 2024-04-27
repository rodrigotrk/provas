package com.applydigital.hackernews.infrastructure.util;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, cb) -> {
            if (term == null || term.trim().isEmpty()) {
                return null;
            }
            return cb.like(cb.upper(root.get(prop)), SqlUtils.like(term));
        };
    }

    public static <T> Specification<T> conjunction(){
        return (root, query, cb) -> cb.conjunction();
    }

    public static <T> Specification<T> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }
}
