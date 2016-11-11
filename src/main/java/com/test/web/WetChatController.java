package com.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.test.common.JSSDK_Config;
import com.test.enums.Level;
import com.test.message.Message;
import com.test.util.Logger;
import com.test.web.util.GlobalConstants;

import java.util.Map;

/**
 * 前端用户微信配置获取
 * Created by zggdczfr on 2016/10/29.
 */
@Controller
@RequestMapping("/wetechatconfig")
public class WetChatController {

    private static final Logger LOGGER = Logger.getLogger(WetChatController.class);

    @ResponseBody
    @RequestMapping(value = "jssdk")
    public Message JSSDK_config(@RequestParam(value = "url", required = true)String url){
        try {
            System.out.println("检查前端ajax提交的URL === " + url);
            Map<String, String> configMap = JSSDK_Config.jssdk_Sign(url);
            System.out.println(3);
            System.out.println(configMap);

            configMap.put("url", url);
            configMap.put("jsapi_ticket", GlobalConstants.getInterfaceUrl("jsapi_ticket"));
            return Message.success(configMap);

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "JSSDK的ajax请求发生异常 : {0}", e);
            return Message.error();
        }
    }
}
