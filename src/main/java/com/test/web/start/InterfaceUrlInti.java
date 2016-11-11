package com.test.web.start;

import com.test.web.util.GlobalConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 项目启动的初始化方法
 * Created by zggdczfr on 2016/10/23.
 */
public class InterfaceUrlInti {

    public synchronized static void init(){

        ClassLoader class1 = Thread.currentThread().getContextClassLoader();
        Properties propertie = new Properties();
        if (GlobalConstants.interfaceUrlProperties == null){
            GlobalConstants.interfaceUrlProperties = new Properties();
        }

        InputStream inPut = null;
        try {
            inPut = class1.getResourceAsStream("/interface_url.properties");
            propertie.load(inPut);
            for (Object key : propertie.keySet()){
                GlobalConstants.interfaceUrlProperties.put(key, propertie.get(key));
            }

            propertie = new Properties();
            inPut = class1.getResourceAsStream("/wechat.properties");
            propertie.load(inPut);
            for (Object key : propertie.keySet()){
                GlobalConstants.interfaceUrlProperties.put(key, propertie.get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inPut != null){
                try {
                    inPut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return;
    }
}
