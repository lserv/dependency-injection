package io.lenur.di.util;

public class ClassName {
    private static final int CLASS_EXTENSION = 6;

    public static String normalize(String packageName, String fileName) {
        StringBuilder builder = new StringBuilder(packageName);
        String modified = fileName
                .replace('/', '.')
                .substring(0, fileName.length() - CLASS_EXTENSION);

        if (!modified.startsWith(".")) {
            builder.append(".");
        }
        builder.append(modified);

        return builder.toString();
    }
}
