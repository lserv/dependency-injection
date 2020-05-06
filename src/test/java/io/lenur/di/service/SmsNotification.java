package io.lenur.di.service;

public class SmsNotification implements Notification {
    @Override
    public String send() {
        return "Sms notification";
    }

    @Override
    public int executeCommand(int a, int b) {
        return 0;
    }
}
