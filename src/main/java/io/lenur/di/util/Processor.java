package io.lenur.di.util;

import io.lenur.di.annotation.Dependencies;
import io.lenur.di.annotation.Instance;
import io.lenur.di.exception.DependencyException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {
    private final Map<Class<?>, Object> dependencies = new HashMap<>();

    public Map<Class<?>, Object> getDependencies() {
        return dependencies;
    }

    public void process(final List<Class<?>> classes) {
        for (Class<?> clazz: classes) {
            if (clazz.isAnnotationPresent(Dependencies.class)) {
                this.processInstance(clazz);
            }
        }
    }

    private void processInstance(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        try {
            Constructor<?> constructor = clazz.getConstructor();
            Object config = constructor.newInstance();

            for (Method method: methods) {
                if (method.isAnnotationPresent(Instance.class)) {
                    dependencies.put(method.getReturnType(), method.invoke(config));
                }
            }
        } catch (NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException
                | InstantiationException e
        ) {
            throw new DependencyException(e.getMessage());
        }
    }
}
