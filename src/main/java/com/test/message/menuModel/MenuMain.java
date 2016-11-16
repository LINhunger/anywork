package com.test.message.menuModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.test.common.WetChatTest;
import com.test.util.HttpUtils;
import com.test.web.util.GlobalConstants;

/**
 * Created by zggdczfr on 2016/11/7.
 */
public class MenuMain {

    public static void main(String[] args) throws Exception {
        ClickButton myInformation = new ClickButton();
        myInformation.setKey("myInformation");
        myInformation.setName("信息修改");
        myInformation.setType("click");

        ClickButton myOrgan = new ClickButton();
        myOrgan.setKey("myOrgan");
        myOrgan.setName("我的组织");
        myOrgan.setType("click");

        ViewButton viewButton = new ViewButton();
        viewButton.setType("view");
        viewButton.setName("jssdk验证");
        viewButton.setUrl(GlobalConstants.interfaceUrlProperties.get("servlet_url")
                +"html/wechat/jssdk.jsp");

        ViewButton baidu = new ViewButton();
        baidu.setType("view");
        baidu.setName("百度一下");
        baidu.setUrl("https://www.baidu.com/");

        JSONArray sub_button = new JSONArray();
        sub_button.add(myInformation);
        sub_button.add(myOrgan);
        sub_button.add(viewButton);

        JSONObject buttonOne = new JSONObject();
        buttonOne.put("name", "菜单");
        buttonOne.put("sub_button", sub_button);

        //最后包装发给微信
        JSONArray button = new JSONArray();
        button.add(buttonOne);
        button.add(baidu);
        button.add(viewButton);

        JSONObject menujson = new JSONObject();
        menujson.put("button", button);


        WetChatTest wetchat = new WetChatTest();
        wetchat.getToken_getTicket();
        String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
                + GlobalConstants.interfaceUrlProperties.get("access_token");

        try{
            String response = HttpUtils.sendPostBuffers(url, menujson.toString());
            System.out.println("自定义菜单:" + response);
        }catch(Exception e){
            System.out.println("请求错误！");
        }
    }
}
