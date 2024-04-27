package com.applydigital.hackernews.domain.data.user;

import com.applydigital.hackernews.domain.data.tag.Tag;
import com.applydigital.hackernews.domain.validation.Error;
import com.applydigital.hackernews.domain.validation.ValidationHandler;
import com.applydigital.hackernews.domain.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator extends Validator {

    private final User user;

    protected UserValidator(final User user, final ValidationHandler validationHandler){
        super(validationHandler);
        this.user = user;
    }

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    @Override
    public void validate() {
        checkNameConstraints();
        checkEmailConstraints();
        checkPasswordConstraints();
        checkCreatedAtConstraints();
    }

    private void checkNameConstraints(){
        final var name = this.user.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
        }
    }

    private void checkEmailConstraints(){
        final var email = this.user.getEmail();
        if (email == null) {
            this.validationHandler().append(new Error("'email' should not be null"));
            return;
        }

        if (email.isBlank()) {
            this.validationHandler().append(new Error("'email' should not be empty"));
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if(!matcher.matches()){
            this.validationHandler().append(new Error("'email' format invalid"));
        }
    }

    private void checkPasswordConstraints(){
        final var password = this.user.getPassword();
        if (password == null) {
            this.validationHandler().append(new Error("'password' should not be null"));
            return;
        }

        if (password.isBlank()) {
            this.validationHandler().append(new Error("'password' should not be empty"));
        }
    }

    private void checkCreatedAtConstraints(){
        if(this.user.getCreatedAt() == null){
            this.validationHandler().append(new Error("'createdAt' should not be null"));
        }
    }



}
