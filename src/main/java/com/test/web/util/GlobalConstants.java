package com.test.web.util;

import java.util.Properties;

/**
 * Created by zggdczfr on 2016/10/25.
 */
public class GlobalConstants {

    public static Properties interfaceUrlProperties;

    public static String getInterfaceUrl(String key){
        return (String) interfaceUrlProperties.get(key);
    }
}
