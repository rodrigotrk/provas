package com.applydigital.hackernews.domain.data.tag;

import com.applydigital.hackernews.domain.data.article.Article;
import com.applydigital.hackernews.domain.validation.Error;
import com.applydigital.hackernews.domain.validation.ValidationHandler;
import com.applydigital.hackernews.domain.validation.Validator;

public class TagValidator extends Validator {

    private final Tag tag;

    protected TagValidator(final Tag tag, final ValidationHandler validationHandler){
        super(validationHandler);
        this.tag = tag;
    }

    @Override
    public void validate() {
        checkTagConstraints();
        checkCreatedAtConstraints();
        checkUpdatedAtConstraints();
    }

    private void checkTagConstraints(){
        final var author = this.tag.getTag();
        if (author == null) {
            this.validationHandler().append(new Error("'tag' should not be null"));
            return;
        }

        if (author.isBlank()) {
            this.validationHandler().append(new Error("'tag' should not be empty"));
        }
    }

    private void checkCreatedAtConstraints(){
        if(this.tag.getCreatedAt() == null){
            this.validationHandler().append(new Error("'createdAt' should not be null"));
        }
    }

    private void checkUpdatedAtConstraints(){
        if(this.tag.getUpdatedAt() == null){
            this.validationHandler().append(new Error("'updatedAt' should not be null"));
        }
    }

}
