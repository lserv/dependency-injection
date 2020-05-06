package io.lenur.di.service;

import io.lenur.di.annotation.Inject;

public class SubtractCommand implements Command {
    @Inject
    private Notification notification;

    @Override
    public int execute(int a, int b) {
        return a - b;
    }

    @Override
    public String sendNotification() {
        return notification.send();
    }
}
