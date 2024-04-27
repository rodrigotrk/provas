package com.applydigital.hackernews.domain.data.user;

import com.applydigital.hackernews.domain.data.Identifier;
import com.applydigital.hackernews.domain.util.IdUtils;

import java.util.Objects;

public class UserID extends Identifier {
    private final String value;

    public UserID(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static UserID from(final String id){
        return new UserID(id);
    }

    public static UserID unique(){
        return UserID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserID that = (UserID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
