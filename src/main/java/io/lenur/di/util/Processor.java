package io.lenur.di.util;

import io.lenur.di.annotation.Config;
import io.lenur.di.annotation.Inject;
import io.lenur.di.annotation.Instance;
import io.lenur.di.exception.DependencyException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Processor {
    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final List<Class<?>> injectClasses = new LinkedList<>();

    public Map<Class<?>, Object> getInstances() {
        return instances;
    }

    public List<Class<?>> getInjectClasses() {
        return injectClasses;
    }

    public void process(final List<Class<?>> classes) {
        for (Class<?> clazz: classes) {
            if (clazz.isAnnotationPresent(Config.class)) {
                this.processInstance(clazz);
            }

            this.processInjections(clazz);
        }
    }

    private void processInjections(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                injectClasses.add(clazz);
                break;
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
                    instances.put(method.getReturnType(), method.invoke(config));
                }
            }
        } catch (NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException
                | InstantiationException e
        ) {
            String message = String.format(
                    "Cannot process the %s configuration!",
                    clazz.getName()
            );
            throw new DependencyException(message);
        }
    }
}
