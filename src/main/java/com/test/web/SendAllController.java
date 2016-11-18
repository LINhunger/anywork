package com.test.web;

import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.test.util.HttpUtils;
import com.test.web.util.GlobalConstants;


/**
 * 测试类
 * Created by zggdczfr on 2016/11/12.
 */
@Controller
@RequestMapping(value = "/send")
public class SendAllController {

    @RequestMapping("all")
    public String snedToAll(){
        String url = GlobalConstants.getInterfaceUrl("SendByOpenidUrl")
                + GlobalConstants.interfaceUrlProperties.get("access_token");

        String newsUrl = GlobalConstants.getInterfaceUrl("newsUrl")
                + GlobalConstants.interfaceUrlProperties.get("access_token");

//        //先将群发信息包装好
        JSONArray openid = new JSONArray();
        openid.add("onlp5w3OYiiSNbUuM5Qbvdt-Ic18");
        openid.add("onlp5w8qKlV_wlwqTIq3fLH1czyI");


        JSONObject article = new JSONObject();
        article.put("thumb_media_id", GlobalConstants.getInterfaceUrl("inform_id"));
        article.put("author", "fangrui");
        article.put("title", "群发图文接口测试~");
        article.put("content_source_url", "www.baidu.com");
        article.put("content", "<h1>标题</h1><p>1234567890</p>!!!");
        article.put("digest", "窗口描述界面~");
        article.put("show_cover_pic", "1");

        JSONArray articles = new JSONArray();
        articles.add(article);

        JSONObject object = new JSONObject();
        object.put("articles", articles);

        try{
            //上传图文素材
            String newsResponse = HttpUtils.sendPostBuffers(newsUrl, object.toJSONString());
            String mediaid = net.sf.json.JSONObject.fromObject(newsResponse).getString("media_id");
            //将素材编号包装好
            JSONObject media_id = new JSONObject();
            media_id.put("media_id", mediaid);

            JSONObject realObject = new JSONObject();
            realObject.put("touser", openid);
            realObject.put("mpnews", media_id);
            realObject.put("msgtype", "mpnews");

            String response = HttpUtils.sendPostBuffers(url, realObject.toJSONString());

            System.out.println("群发消息:" + response);
        }catch(Exception e){
            System.out.println("群发消息请求错误！");
        }

        return "/index";
    }
}
