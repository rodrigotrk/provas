package com.applydigital.hackernews.domain.exceptions;

import com.applydigital.hackernews.domain.validation.Error;

import java.util.List;

public class InvalidTokenException extends DomainException{
    protected InvalidTokenException(String aMessage, List<Error> anErrors) {
        super(aMessage, anErrors);
    }
}
