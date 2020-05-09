package io.lenur.di;

import io.lenur.di.exception.DependencyException;
import io.lenur.di.service.Command;
import io.lenur.di.service.AddCommand;
import io.lenur.di.service.Notification;
import io.lenur.di.service.SmsNotification;

import org.junit.Assert;
import org.junit.Test;

public class DependencyTest {
    private final PackageContext packageContext = Dependency.init("io.lenur.di");

    @Test(expected = DependencyException.class)
    public void injectNotification() {
        Command command = (Command) packageContext.getInstance(AddCommand.class);
    }

    @Test
    public void notInjectNotification() {
        Command command = (Command) packageContext.getInstance(Command.class);
        Assert.assertTrue(command.sendNotification().isEmpty());
    }

    @Test(expected = DependencyException.class)
    public void injectCommand() {
        Notification notification = (Notification) packageContext.getInstance(SmsNotification.class);
    }

    @Test
    public void notInjectCommand() {
        Notification notification = (Notification) packageContext.getInstance(Notification.class);
        Assert.assertEquals(notification.executeCommand(1, 2), 0);
    }
}
