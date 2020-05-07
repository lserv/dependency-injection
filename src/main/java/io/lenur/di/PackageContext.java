package io.lenur.di;

import io.lenur.di.util.FileScanner;
import io.lenur.di.util.Injector;
import io.lenur.di.util.Processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageContext {
    private static Map<Class<?>, Object> instancesToInject = new HashMap<>();
    private final FileScanner scanner = new FileScanner();
    private final Processor processor = new Processor();
    private final Injector injector = new Injector();

    public PackageContext(String packageName) {
        if (instancesToInject.size() == 0) {
            List<Class<?>> classes = scanner.getClasses(packageName);
            processor.process(classes);
            instancesToInject = processor.getInstances();
        }
    }

    public Object getInstance(Class<?> clazz) {
        return injector.getInstance(clazz, instancesToInject);
    }
}
