package io.lenur.di.util;

import io.lenur.di.annotation.Inject;
import io.lenur.di.exception.DependencyException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Injector {
    private static final Map<Object, Object> injectedObjects = new HashMap<>();
    private Map<Class<?>, Object> dependencies;

    public Object getInstance(Object object, Map<Class<?>, Object> dependencies) {
        if (injectedObjects.containsKey(object)) {
            return injectedObjects.get(object);
        }
        this.dependencies = dependencies;

        this.inject(object);

        return injectedObjects.get(object);
    }

    private void inject(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field: fields) {
            Object dependency = getDependency(field.getType());
            if (field.isAnnotationPresent(Inject.class)) {
                try {
                    field.setAccessible(true);
                    field.set(object, dependency);
                } catch (IllegalAccessException e) {
                    throw new DependencyException(e.getMessage());
                }
            }
        }

        injectedObjects.put(object, object);
    }

    private Object getDependency(Class<?> clazz) {
        final Object dependency = dependencies.get(clazz);
        Field[] fields = dependency.getClass().getDeclaredFields();

        for (Field field: fields) {
            Object instance = dependencies.get(field.getType());
            if (field.isAnnotationPresent(Inject.class) && null != instance) {
                try {
                    field.setAccessible(true);
                    field.set(dependency, instance);
                } catch (IllegalAccessException e) {
                    throw new DependencyException(e.getMessage());
                }
            }
        }

        return dependency;
    }
}
