package io.lenur.di.service;

public interface Command {
    int execute(int a, int b);

    String sendNotification();
}
