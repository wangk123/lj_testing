package com.lj.testing.server.util;


import com.netflix.astyanax.util.TimeUUIDUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseStringUtils {

    public static String uuid() {
        return TimeUUIDUtils.getUniqueTimeUUIDinMillis().toString();
    }

    public static String uuidSimple() {
        return uuid().replaceAll("-", "");
    }

    /**
     * 首字母大写
     * @param name
     * @return
     */
    public static String initialCapital(String name) {
        if (StringUtils.isNotBlank(name)) {
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return name;
    }

    /**
     * 去掉字符串中空格、换行制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String urlEncode(String str) {
        String result = "";
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String urlDecode(String str) {
        String result = "";
        try {
            result = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


}
