package io.lenur.di.service;

import io.lenur.di.annotation.Inject;

public class EmailNotification implements Notification {
    @Inject
    private Command command;

    @Override
    public String send() {
        return "Email notification";
    }

    @Override
    public int executeCommand(int a, int b) {
        return command.execute(a, b);
    }
}
