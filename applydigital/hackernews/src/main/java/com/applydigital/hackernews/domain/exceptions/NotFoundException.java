package com.applydigital.hackernews.domain.exceptions;

import com.applydigital.hackernews.domain.data.AggregateRoot;
import com.applydigital.hackernews.domain.data.Identifier;
import com.applydigital.hackernews.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends  DomainException{

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id
    ) {
        final var anError = "%s with ID %s was not found".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );
        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            String field,
            String value
    ) {
        final var anError = "%s was field %s with %s not found".formatted(
                anAggregate.getSimpleName(),
                field,
                value
        );
        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(final Error error) {
        return new NotFoundException(error.message(), List.of(error));
    }
}
