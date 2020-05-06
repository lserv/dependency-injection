package io.lenur.di.util;

import io.lenur.di.annotation.Inject;
import io.lenur.di.exception.DependencyException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Injector {
    public Object inject(Class<?> clazz, Map<Class<?>, Object> instances) {
        Field[] fields = clazz.getDeclaredFields();
        Object object = this.getInstance(clazz);

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

        return object;
    }

    private Object getInstance(Class<?> clazz) {
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
