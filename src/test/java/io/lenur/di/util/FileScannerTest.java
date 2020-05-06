package io.lenur.di.util;

import io.lenur.di.config.Instances;
import io.lenur.di.exception.DependencyException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FileScannerTest {
    @Test
    public void scanClass() {
        FileScanner scanner = new FileScanner();
        List<Class<?>> classes = scanner.getClasses("io.lenur.di.config");

        Assert.assertEquals(1, classes.size());
        Assert.assertEquals(Instances.class, classes.get(0));
    }

    @Test(expected = DependencyException.class)
    public void scanWrongPackageName() {
        FileScanner scanner = new FileScanner();
        List<Class<?>> classes = scanner.getClasses("io.lenur.di.config1");
    }
}
