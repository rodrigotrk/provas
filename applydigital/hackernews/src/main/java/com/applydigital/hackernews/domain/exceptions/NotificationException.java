package com.applydigital.hackernews.domain.exceptions;

import com.applydigital.hackernews.domain.validation.handler.Notification;

public class NotificationException extends  DomainException{
    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }
}
