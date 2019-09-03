package com.lj.testing.server.util;

import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class BeanCopyUtil {

    private static DozerBeanMapper dozer = new DozerBeanMapper();

    public BeanCopyUtil() {
    }

    public static <T> T objConvert(Object obj, Class<T> toObj) {
        return null == obj ? null : dozer.map(obj, toObj);
    }

    public static void copy(Object source, Object toObj) {
        if (null != source) {
            dozer.map(source, toObj);
        }

    }

    public static <T> List<T> convertList(Collection<?> collection, Class<T> toObj) {
        if (collection == null) {
            return null;
        } else if (toObj == null) {
            return null;
        } else {
            List<T> list = new ArrayList();
            Iterator var3 = collection.iterator();

            while(var3.hasNext()) {
                Object obj = var3.next();
                list.add(dozer.map(obj, toObj));
            }

            return list;
        }
    }

    /**
     * 对象深度克隆
     * @param source
     * @return
     */
    public static <T> T depthClone(Object source, Class<T> cls) {
        Object tarage = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(source);
            oos.close();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            tarage = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("数据Bean克隆失败！");
        }
        if (cls.isInstance(tarage)) {
            return cls.cast(tarage);
        } else {
            log.info("造型成目标类:{}失败，返回null", cls);
            return null;
        }
    }
}
