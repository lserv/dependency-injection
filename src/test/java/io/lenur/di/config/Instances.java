package io.lenur.di.config;

import io.lenur.di.annotation.Config;
import io.lenur.di.annotation.Instance;
import io.lenur.di.service.AddCommand;
import io.lenur.di.service.Command;
import io.lenur.di.service.Notification;
import io.lenur.di.service.SmsNotification;

@Config
public class Instances {
    @Instance
    public Notification getNotification() {
        return new SmsNotification();
    }

    @Instance
    public Command getCommand() {
        return new AddCommand();
    }
}
