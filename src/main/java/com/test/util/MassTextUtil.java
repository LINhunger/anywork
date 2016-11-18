package com.test.util;

import com.alibaba.fastjson.JSONObject;
import com.test.service.RelationService;
import com.test.service.impl.RelationServiceImpl;
import com.test.web.util.GlobalConstants;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微信群发工具类
 * Created by zggdczfr on 2016/11/12.
 */
public class MassTextUtil {

    /**
     * 群发返回结构 “send success”或“send fail”或“err(num)”
     * 详细文档可查看微信公众号平台开发者文档
     * err(10001), //涉嫌广告 err(20001), //涉嫌政治 err(20004), //涉嫌社会
     * err(20002), //涉嫌色情 err(20006), //涉嫌违法犯罪 err(20008), //涉嫌欺诈
     * err(20013), //涉嫌版权 err(22000), //涉嫌互推(互相宣传) err(21000), //涉嫌其他
     */


    /**
     * 微信图文群发工具
     * @param openidList openidid集合
     * @param type 图文类型，公告为0，作业为1
     * @param objectId 对应markId
     * @param author 发布者
     * @return
     * @throws Exception
     */
    public static String wetchatSend(List<String> openidList, int type, int objectId, String author) throws Exception{

        //群发URL
        String url = GlobalConstants.getInterfaceUrl("SendByOpenidUrl")
                + GlobalConstants.interfaceUrlProperties.get("access_token");
        //上传图文信息URL
        String newsUrl = GlobalConstants.getInterfaceUrl("newsUrl")
                + GlobalConstants.interfaceUrlProperties.get("access_token")+"&type=news";


        String sendUrl = GlobalConstants.getInterfaceUrl("servlet_url")
                + "src/html/wechat/news.html?type=" +
                +type+"&markId="+objectId;     //图文跳转链接
        String picture = null;      //图文封面
        String content = null;      //图文内容
        String title = null;        //图文标题
        String digest = null;       //图文描述

        //确定组织内微信用户
//        List<String> openidList = relationService.selectOpenidByOrganId(organId);
        JSONArray openid = new JSONArray();
        openid.addAll(openidList);

        if (0 == type){
            //公告群发
            picture = GlobalConstants.getInterfaceUrl("inform_id");
            content = "管理员 "+author+" 向您发送了一条公告通知，快点点击\"阅读原文\"查看吧~";
            title = "新公告通知！";
            digest = "您收到了一条公告通知，快点点击查看吧~";
        } else {
            //作业群发
            picture = GlobalConstants.getInterfaceUrl("question_id");
            content = "管理员 "+author+" 向您发送了一份作业通知，快点点击\"阅读原文\"查看吧~";
            title = "新作业通知！";
            digest = "您收到了一份作业通知，快点点击查看吧~";
        }

        //包装微信图文
        JSONObject article = new JSONObject();
        article.put("thumb_media_id", picture);
        article.put("author", author);
        article.put("title", title);
        article.put("content_source_url", sendUrl);
        article.put("content", content);
        article.put("digest", digest);
        article.put("show_cover_pic", "1");

        JSONArray articles = new JSONArray();
        articles.add(article);

        JSONObject object = new JSONObject();
        object.put("articles", articles);


        //上传图文素材
        String newsResponse = HttpUtils.sendPostBuffers(newsUrl, object.toString());
        String mediaid = net.sf.json.JSONObject.fromObject(newsResponse).getString("media_id");
        //将素材编号包装好
        JSONObject media_id = new JSONObject();
        media_id.put("media_id", mediaid);

        //设置包装参数
        JSONObject realObject = new JSONObject();
        realObject.put("touser", openid);
        realObject.put("mpnews", media_id);
        realObject.put("msgtype", "mpnews");

        String response = HttpUtils.sendPostBuffers(url, realObject.toJSONString());

        return response;
    }
}
