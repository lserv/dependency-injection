package io.lenur.di;

import io.lenur.di.service.Command;
import io.lenur.di.service.SubtractCommand;
import io.lenur.di.service.AddCommand;
import io.lenur.di.service.EmailNotification;
import io.lenur.di.service.Notification;
import io.lenur.di.service.SmsNotification;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DependencyTest {
    @Before
    public void init() {
        Dependency.init("io.lenur.di");
    }

    @Test
    public void injectNotification() {
        Command command = (Command) Dependency.getInstance(SubtractCommand.class);
        Assert.assertEquals(command.sendNotification(), "Sms notification");
    }

    @Test
    public void notInjectNotification() {
        Command command = (Command) Dependency.getInstance(AddCommand.class);
        Assert.assertTrue(command.sendNotification().isEmpty());
    }

    @Test
    public void injectCommand() {
        Notification notification = (Notification) Dependency.getInstance(EmailNotification.class);
        Assert.assertEquals(notification.executeCommand(1, 2), 3);
    }

    @Test
    public void notInjectCommand() {
        Notification notification = (Notification) Dependency.getInstance(SmsNotification.class);
        Assert.assertEquals(notification.executeCommand(1, 2), 0);
    }
}
