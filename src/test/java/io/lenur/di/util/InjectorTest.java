package io.lenur.di.util;

import io.lenur.di.service.AddCommand;
import io.lenur.di.service.Command;
import io.lenur.di.service.Notification;
import io.lenur.di.service.EmailNotification;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class InjectorTest {
    @Test
    public void processClasses() {
        Map<Class<?>, Object> instancesToInject = new HashMap<>();
        instancesToInject.put(Command.class, new AddCommand());

        Injector injector = new Injector();
        Notification notification = (Notification) injector.getInstance(EmailNotification.class, instancesToInject);
        Assert.assertEquals(notification.executeCommand(1, 2), 3);
    }
}
