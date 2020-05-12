package io.lenur.di.util;

import org.junit.Assert;
import org.junit.Test;

public class ClassNameTest {
    @Test
    public void normalize() {
        String packageName = "io.lenur.di";
        String className = "/service/AddCommand.class";
        String result = ClassName.normalize(packageName, className);

        Assert.assertEquals("io.lenur.di.service.AddCommand", result);
    }

    @Test
    public void normalizeWithNoSlash() {
        String packageName = "io.lenur.di";
        String className = "service/AddCommand.class";
        String result = ClassName.normalize(packageName, className);

        Assert.assertEquals("io.lenur.di.service.AddCommand", result);
    }
}
