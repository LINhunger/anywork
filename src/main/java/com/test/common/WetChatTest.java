package com.test.common;

import net.sf.json.JSONObject;
import com.test.util.HttpUtils;
import com.test.web.util.GlobalConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信两小时定时任务体
 * 获取Token值
 * 获取jsticket值
 * Created by zggdczfr on 2016/11/7.
 */
public class WetChatTest {

    public void getToken_getTicket() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        //获取Token值
        params.put("grant_type", "client_credential");
        params.put("appid", GlobalConstants.getInterfaceUrl("appid"));
        params.put("secret", GlobalConstants.getInterfaceUrl("AppSecret"));
        String jsonToken = HttpUtils.sendGet(
                GlobalConstants.getInterfaceUrl("tokenUrl"), params);
        //获取到token并赋值保存
        String access_token = JSONObject.fromObject(jsonToken).getString("access_token");
        GlobalConstants.interfaceUrlProperties.put("access_token", access_token);

        //获取jsticket值
        params.clear();
        params.put("access_token", access_token);
        params.put("type", "jsapi");
        String jsticket = HttpUtils.sendGet(
                GlobalConstants.getInterfaceUrl("ticketUrl"), params);
        String jsapi_ticket = JSONObject.fromObject(jsticket).getString("ticket");
        GlobalConstants.interfaceUrlProperties.put("jsapi_ticket", jsapi_ticket);

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new Date())+"token为==============================" + access_token);
        System.out.println("jsapi_ticket============================" + jsapi_ticket);
    }
}
