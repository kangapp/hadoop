package com.hadoop.hdfs;


import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 读取配置文件
 */
public class ParamsUtils {

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(ParamsUtils.class.getClassLoader().getResourceAsStream("wc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void main(String[] args) {
        Properties properties = ParamsUtils.getProperties();
        Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()){
            Object key = enumeration.nextElement();
            System.out.println(properties.get(key));
        }
    }
}
