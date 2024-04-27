package com.applydigital.hackernews.domain.data;

public abstract class AggregateRoot <ID extends Identifier> extends Entity<ID>{
    protected AggregateRoot(ID id) {
        super(id);
    }
}
