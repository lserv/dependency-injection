package io.lenur.di;

import io.lenur.di.util.FileScanner;
import io.lenur.di.util.Injector;
import io.lenur.di.util.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Dependency {
    private static Map<Class<?>, Object> instances = new HashMap<>();
    private static final FileScanner scanner = new FileScanner();
    private static final Processor processor = new Processor();
    private static final Injector injector = new Injector();

    public static void init(String packageName) {
        if (instances.size() == 0) {
            final List<Class<?>> classes = scanner.getClasses(packageName);
            processor.process(classes);
            instances = processor.getInstances();
        }
    }

    public static Object getInstance(Class<?> clazz) {
        return injector.inject(clazz, instances);
    }
}
