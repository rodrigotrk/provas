package com.applydigital.hackernews.domain.data.tag;

import com.applydigital.hackernews.domain.data.Identifier;
import com.applydigital.hackernews.domain.util.IdUtils;

import java.util.Objects;

public class TagID extends Identifier {
    private final String value;

    public TagID(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static TagID from(final String id){
        return new TagID(id);
    }

    public static TagID unique(){
        return TagID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TagID that = (TagID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
