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
    private static final int CLASS_EXTENSION = 6;

    public List<Class<?>> getClasses(String packageName) {
        final URL url = this.obtainPath(packageName);
        final List<String> fileNames = this.getFileNames(url);

        return this.mapToClasses(fileNames, packageName);
//        List<String> classes;
//
//        try (Stream<Path> files = Files.walk(Paths.get(url.getFile()))) {
//            classes = files
//                    .map(x -> x.toString().replace(url.getFile(), ""))
//                    .filter(f -> f.endsWith(".class"))
//                    .map(x -> {
//                        String fileName = x.replace('/', '.');
//                        return fileName.substring(0, fileName.length() - CLASS_EXTENSION);
//                    })
//                    .collect(Collectors.toList());
//
//        }
//
//        return classes;
    }

    private List<Class<?>> mapToClasses(List<String> fileNames, String packageName) {
        final List<Class<?>> classes = new LinkedList<>();
        for (String fileName: fileNames) {
            String modified = fileName
                    .replace('/', '.')
                    .substring(0, fileName.length() - CLASS_EXTENSION);

            Class<?> clazz = loadClass(packageName + modified);

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
            String message = String.format(
                    "The class name %s was not found!",
                    className
            );
            throw new DependencyException(message);
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

        final String packageTrans = packageName.replace('.', '/');
        final URL url = classLoader.getResource(packageTrans);

        if (url == null) {
            final String message = String.format("Cannot locate the package name %s !", packageName);
            throw new DependencyException(message);
        }

        return url;
    }
}
