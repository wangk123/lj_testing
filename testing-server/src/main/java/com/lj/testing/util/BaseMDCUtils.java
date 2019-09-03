package com.lj.testing.util;


import org.slf4j.MDC;

public class BaseMDCUtils {

    public static String get(String key) {
        return MDC.get(key);
    }

    public static void put(String key, String value) {
        MDC.put(key, value);
    }

    public static void clear() {
        MDC.clear();
    }

    public static void remove(String key) {
        MDC.remove(key);
    }

}
