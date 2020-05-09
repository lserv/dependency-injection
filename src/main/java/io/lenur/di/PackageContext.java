package io.lenur.di;

import io.lenur.di.exception.DependencyException;
import io.lenur.di.util.FileScanner;
import io.lenur.di.util.Injector;
import io.lenur.di.util.Processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageContext {
    private static Map<Class<?>, Object> dependencies = new HashMap<>();
    private final FileScanner scanner = new FileScanner();
    private final Processor processor = new Processor();
    private final Injector injector = new Injector();

    public PackageContext(String packageName) {
        if (dependencies.size() == 0) {
            List<Class<?>> classes = scanner.getClasses(packageName);
            processor.process(classes);
            dependencies = processor.getDependencies();
        }
    }

    public Object getInstance(Class<?> clazz) {
        Object object = getObjectInstance(clazz);

        return injector.getInstance(object, dependencies);
    }

    private Object getObjectInstance(Class<?> clazz) {
        if (!dependencies.containsKey(clazz)) {
            String msg = String.format("The class %s is not defined in dependecies!", clazz);
            throw new DependencyException(msg);
        }

        return dependencies.get(clazz);
    }
}
