package com.applydigital.hackernews.domain.data.article;

import com.applydigital.hackernews.domain.validation.Error;
import com.applydigital.hackernews.domain.validation.ValidationHandler;
import com.applydigital.hackernews.domain.validation.Validator;

public class ArticleValidator extends Validator {

    private final Article article;

    protected ArticleValidator(final Article article, final ValidationHandler validationHandler){
        super(validationHandler);
        this.article = article;
    }

    @Override
    public void validate() {
        checkAuthorConstraints();
        checkObjectIDConstraints();
        checkCreatedAtConstraints();
        checkUpdatedAtConstraints();
        checkTagConstraints();
    }

    private void checkAuthorConstraints(){
        final var author = this.article.getAuthor();
        if (author == null) {
            this.validationHandler().append(new Error("'author' should not be null"));
            return;
        }

        if (author.isBlank()) {
            this.validationHandler().append(new Error("'author' should not be empty"));
        }
    }

    private void checkObjectIDConstraints(){
        final var objectId = this.article.getObjectId();
        if (objectId == null) {
            this.validationHandler().append(new Error("'objectId' should not be null"));
            return;
        }

        if (objectId.isBlank()) {
            this.validationHandler().append(new Error("'objectId' should not be empty"));
        }
    }

    private void checkCreatedAtConstraints(){
        if(this.article.getCreatedAt() == null){
            this.validationHandler().append(new Error("'createdAt' should not be null"));
        }
    }

    private void checkUpdatedAtConstraints(){
        if(this.article.getUpdatedAt() == null){
            this.validationHandler().append(new Error("'updatedAt' should not be null"));
        }
    }

    private void checkTagConstraints(){
        if(this.article.getTags() == null){
            this.validationHandler().append(new Error("'tags' should not be null"));
        }
    }
}
