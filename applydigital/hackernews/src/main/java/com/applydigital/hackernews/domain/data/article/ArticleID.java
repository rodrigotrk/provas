package com.applydigital.hackernews.domain.data.article;

import com.applydigital.hackernews.domain.data.Identifier;
import com.applydigital.hackernews.domain.util.IdUtils;

import java.util.Objects;

public class ArticleID extends Identifier {
    private final String value;

    public ArticleID(String value) {
        this.value =  Objects.requireNonNull(value);
    }

    public static ArticleID from(final String id){
        return new ArticleID(id);
    }

    public static ArticleID unique() {
        return ArticleID.from(IdUtils.uuid());
    }

    @Override
    public String getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ArticleID that = (ArticleID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
