package com.lj.testing.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.HashMap;

public class EnvConf {

    public String currentEnv = "test";
    public HashMap<String, HashMap<String, String>> env;

    private EnvConf() {}
    private volatile static EnvConf envConf = null;
    public static EnvConf getInstance() {
        if (envConf == null) {
            synchronized (EnvConf.class) {
                if (envConf == null) {
                    envConf = loadConfig();
                }
            }
        }
        return envConf;
    }

    /**
     * 加载配置文件
     * @return
     */
    private static EnvConf loadConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(EnvConf.class.getResourceAsStream("/conf/envConfig.yaml"), EnvConf.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getValue(String key) {
        return envConf.env.get(envConf.currentEnv).get(key);
    }

}
