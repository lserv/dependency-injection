package io.lenur.di.service;

public class AddCommand implements Command {
    @Override
    public int execute(int a, int b) {
        return a + b;
    }

    @Override
    public String sendNotification() {
        return "";
    }
}
