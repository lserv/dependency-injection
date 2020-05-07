package io.lenur.di.util;

import io.lenur.di.annotation.Inject;
import io.lenur.di.exception.DependencyException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Injector {
    private static final Map<Class<?>, Object> injectInstances = new HashMap<>();

    public Object getInstance(Class<?> clazz, Map<Class<?>, Object> instances) {
        if (injectInstances.containsKey(clazz)) {
            return injectInstances.get(clazz);
        }

        this.inject(clazz, instances);

        return injectInstances.get(clazz);
    }

    private void inject(Class<?> clazz, Map<Class<?>, Object> instances) {
        Field[] fields = clazz.getDeclaredFields();
        Object object = this.newInstance(clazz);

        for (Field field: fields) {
            Object inject = instances.get(field.getType());
            if (field.isAnnotationPresent(Inject.class) && null != inject) {
                try {
                    field.setAccessible(true);
                    field.set(object, inject);
                } catch (IllegalAccessException e) {
                    throw new DependencyException(e.getMessage());
                }
            }
        }

        injectInstances.put(clazz, object);
    }

    private Object newInstance(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException
                | InstantiationException e
        ) {
            throw new DependencyException(e.getMessage());
        }
    }
}
