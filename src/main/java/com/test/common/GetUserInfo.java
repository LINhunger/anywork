package com.test.common;

import net.sf.json.JSONObject;
import com.test.util.HttpUtils;
import com.test.web.util.GlobalConstants;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Created by zggdczfr on 2016/11/7.
 */
public class GetUserInfo {

    public static HashMap<String, String> getUserInfoByOpenID(String openid) throws IOException, URISyntaxException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", GlobalConstants.getInterfaceUrl("access_token"));
        params.put("openid", openid);
        params.put("lang", "zh_CN");
        //发送请求
        String result = HttpUtils.sendGet(GlobalConstants.getInterfaceUrl("OpenidUserInfoUrl"), params);
        System.out.println("获取用户信息: " + result);
        params.clear();
        //具体可查看微信开发者文档
        params.put("nickname", JSONObject.fromObject(result).getString("nickname")); //昵称
        params.put("headimgurl", JSONObject.fromObject(result).getString("headimgurl"));  //图像
        params.put("sex", JSONObject.fromObject(result).getString("sex"));  //性别

        return params;
    }
}
