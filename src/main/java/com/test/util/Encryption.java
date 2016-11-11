package com.test.util;

import org.springframework.util.DigestUtils;

/**
 * Created by hunger on 2016/11/5.
 */
public class Encryption {

    //md5盐值字符串
    private  static final String slat = "livid";

    public static String  getMD5 (String string) {
        String base = string+"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return  md5;
    }
}
