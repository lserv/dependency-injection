package io.lenur.di.util;

import io.lenur.di.annotation.Inject;
import io.lenur.di.exception.DependencyException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Injector {
    private static final Map<Object, Object> injectedObjects = new HashMap<>();

    public Object getInstance(Object object, Map<Class<?>, Object> dependencies) {
        if (injectedObjects.containsKey(object)) {
            return injectedObjects.get(object);
        }

        this.inject(object, dependencies);

        return injectedObjects.get(object);
    }

    private void inject(Object object, Map<Class<?>, Object> dependencies) {
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field: fields) {
            Object dependency = dependencies.get(field.getType());
            if (field.isAnnotationPresent(Inject.class) && null != dependency) {
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
}
