package io.lenur.di;

import java.util.HashMap;
import java.util.Map;

public class Dependency {
    private static final Map<String, PackageContext> contexts = new HashMap<>();

    public static PackageContext init(String packageName) {
        if (!contexts.containsKey(packageName)) {
            PackageContext applicationContext = new PackageContext(packageName);
            contexts.put(packageName, applicationContext);
        }

        return contexts.get(packageName);
    }
}
