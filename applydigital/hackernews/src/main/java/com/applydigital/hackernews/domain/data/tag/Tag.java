package com.applydigital.hackernews.domain.data.tag;


import com.applydigital.hackernews.domain.data.Entity;
import com.applydigital.hackernews.domain.exceptions.NotificationException;
import com.applydigital.hackernews.domain.validation.ValidationHandler;
import com.applydigital.hackernews.domain.validation.handler.Notification;

import java.time.Instant;

public class Tag extends Entity<TagID> {

    private String tag;

    private Instant createdAt;

    private Instant updatedAt;

    public Tag(TagID tagID, String tag, Instant createdAt, Instant updatedAt) {
        super(tagID);
        this.tag = tag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        selfValidate();
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new TagValidator(this, handler).validate();
    }

    public static Tag newTag(final String tag){
        var now = Instant.now();
        return new Tag(TagID.unique(), tag, now, now);
    }

    public static Tag with(final TagID tagID, final String tag, final Instant createdAt, final Instant updatedAt){
        return new Tag(tagID, tag, createdAt, updatedAt);
    }

    public static Tag with(final Tag tag){
        return with(tag.getId(), tag.tag, tag.createdAt, tag.updatedAt);
    }

    public Tag update(final String tag){
        this.tag = tag;
        this.updatedAt = Instant.now();
        selfValidate();
        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to create a Aggregate Tag", notification);
        }
    }

    public String getTag() {
        return tag;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
