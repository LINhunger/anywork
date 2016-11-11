package com.test.common;

import com.test.web.util.GlobalConstants;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.UUID;

/**
 * 用户微信前端页面的jssdk配置使用
 * Created by zggdczfr on 2016/10/29.
 */
public class JSSDK_Config {

    /**
     * 前端jssdk页面配置需要用到的配置参数
     * @param url
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> jssdk_Sign(String url) throws Exception {
        System.out.println(1);
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        //GlobalConstants.getInterfaceUrl("timestamp");
        String jsapi_ticket = GlobalConstants.getInterfaceUrl("jsapi_ticket");
        // 注意这里参数名必须全部小写，且必须有序
        String  string = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
                + "&timestamp=" + timestamp  + "&url=" + url;

        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string.getBytes("UTF-8"));
        String signature = byteToHex(crypt.digest());
        HashMap<String, String> jssdk = new HashMap<String, String>();
        jssdk.put("appId", GlobalConstants.getInterfaceUrl("appid"));
        jssdk.put("timestamp", timestamp);
        jssdk.put("nonceStr", nonce_str);
        jssdk.put("signature", signature);
        System.out.println(2);
        return jssdk;
    }

    /**
     * 格式化输出
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash){
        Formatter formatter = new Formatter();
        for (byte b : hash){
            //把b这个变量数据以十六进制的格式输出
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 自动生成主键
     * @return
     */
    private static String create_nonce_str(){
        return UUID.randomUUID().toString();
    }

    /**
     * 自动生成时间戳
     * @return
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
