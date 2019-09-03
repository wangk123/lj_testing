package com.lj.testing.util;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BaseKeyValue {

    private static String DEFAULT_KEY = "#default#";
    private static InheritableThreadLocal<Map<String, Object>> threadLocal = new InheritableThreadLocal<>();

    public static void put(String key, Object value) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "key不能为空");
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = Collections.synchronizedMap(new HashMap<>());
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "key不能为空");
        Map map = threadLocal.get();
        return map != null ? map.get(key) : null;
    }

    public static void remove(String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "key不能为空");
        Map map = threadLocal.get();
        if (map != null) {
            map.remove(key);
        }
    }

    public static <T> T get(String key, Class<T> cls) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "key不能为空");
        Object obj = get(key);
        if (cls.isInstance(obj)) {
            return cls.cast(obj);
        }
        return null;
    }

    public static void put(Object value) {
        put(DEFAULT_KEY, value);
    }

    public static Object get() {
        return get(DEFAULT_KEY);
    }

    public static void clear() {
        Map map = threadLocal.get();
        if (map != null) {
            map.clear();
            threadLocal.remove();
        }
    }

}
