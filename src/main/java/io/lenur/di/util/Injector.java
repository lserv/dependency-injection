package io.lenur.di.util;

import io.lenur.di.annotation.Inject;
import io.lenur.di.exception.DependencyException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
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
                    String message = String.format(
                            "Cannot inject the %s class!",
                            field.getType()
                    );
                    throw new DependencyException(message);
                }
            }
        }

        return object;
    }

    public void inject1(Map<Class<?>, Object> instances, List<Class<?>> injectClasses) {
        for (Class<?> clazz: injectClasses) {

            Field[] fields = clazz.getDeclaredFields();
            Object object = this.getInstance(clazz);

            for (Field field: fields) {
                Object inject = instances.get(field.getType());
                if (field.isAnnotationPresent(Inject.class) && null != inject) {
                    try {
                        field.setAccessible(true);
                        field.set(object, inject);
                    } catch (IllegalAccessException e) {
                        String message = String.format(
                                "Cannot inject the %s class!",
                                field.getType()
                        );
                        throw new DependencyException(message);
                    }
                }
            }
        }
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
            String message = String.format(
                    "Cannot create an instance from the %s class!",
                    clazz.getName()
            );
            throw new DependencyException(message);
        }
    }
}
