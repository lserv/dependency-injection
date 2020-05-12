package io.lenur.di.util;

import io.lenur.di.exception.DependencyException;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileScanner {
    public List<Class<?>> getClasses(String packageName) {
        final URL url = this.obtainPath(packageName);
        final List<String> fileNames = this.getFileNames(url);

        return this.mapToClasses(fileNames, packageName);
    }

    private List<Class<?>> mapToClasses(List<String> fileNames, String packageName) {
        final List<Class<?>> classes = new LinkedList<>();

        for (String fileName: fileNames) {

            String className = ClassName.normalize(packageName, fileName);
            Class<?> clazz = loadClass(className);

            if (clazz != null && !clazz.isInterface()) {
                classes.add(clazz);
            }
        }

        return classes;
    }

    private Class<?> loadClass(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new DependencyException(e.getMessage());
        }

        return clazz;
    }

    private List<String> getFileNames(URL url) {
        List<String> fileNames;

        try (Stream<Path> files = Files.walk(Paths.get(url.getFile()))) {
            fileNames = files
                    .map(x -> x.toString().replace(url.getFile(), ""))
                    .filter(f -> f.endsWith(".class"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new DependencyException(e.getMessage());
        }

        return fileNames;
    }

    private URL obtainPath(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String packageNameLoad = packageName.replace('.', '/');
        URL url = classLoader.getResource(packageNameLoad);

        if (url == null) {
            String message = String.format("Cannot locate the package name %s!", packageName);
            throw new DependencyException(message);
        }

        return url;
    }
}
