package com.test.message.menuModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.test.common.WetChatTest;
import com.test.util.HttpUtils;
import com.test.web.util.GlobalConstants;

/**
 * Created by zggdczfr on 2016/10/27.
 */
public class MenuMain {

    public static void main(String[] args) throws Exception {
        ClickButton clickButton = new ClickButton();
        clickButton.setKey("myInformation");
        clickButton.setName("信息修改");
        clickButton.setType("click");

        ViewButton viewButton = new ViewButton();
        viewButton.setType("view");
        viewButton.setName("jssdk验证");
        viewButton.setUrl("http://stmh7.free.natapp.cc/WetChat/jssdk.jsp");

        JSONArray sub_button = new JSONArray();
        sub_button.add(clickButton);
        sub_button.add(viewButton);

        JSONObject buttonOne = new JSONObject();
        buttonOne.put("name", "菜单");
        buttonOne.put("sub_button", sub_button);

        //最后包装发给微信
        JSONArray button = new JSONArray();
        button.add(clickButton);
        button.add(buttonOne);
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
