package io.lenur.di.service;

import io.lenur.di.annotation.Inject;

public class AddCommand implements Command {
    @Inject
    private EmailNotification emailNotification;

    @Override
    public int execute(int a, int b) {
        return a + b;
    }

    @Override
    public String sendNotification() {
        int commandResult = emailNotification.executeCommand(1, 2);

        return String.format("%s %d",
            emailNotification.send(),
            commandResult
        );
    }
}
