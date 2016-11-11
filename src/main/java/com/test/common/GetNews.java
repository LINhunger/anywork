package com.test.common;

import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONArray;
import com.test.util.HttpUtils;
import com.test.web.util.GlobalConstants;

/**
 * 获得微信推送的图文的media_id
 * Created by zggdczfr on 2016/11/3.
 */
public class GetNews {

    /**
     * 将图文打包发送至微信服务器，并获得 media_id
     * @param newsUrl 图文消息的跳转URL
     * @param author  发表图文信息者
     * @param type 图文信息内容(发布公告/发布作业)
     * @return  图文消息的 media_id
     */
    public String getNewsMediaId(String newsUrl, String author, String type){

        String mediaid = null;

        String uploadUrl = GlobalConstants.getInterfaceUrl("newsUrl")
                + GlobalConstants.interfaceUrlProperties.get("access_token");

        //包装jsonObject对象
        JSONObject article = new JSONObject();
        //放置图片
        article.put("thumb_media_id", GlobalConstants.getInterfaceUrl("media_id"));
        //设置发布者
        article.put("author", author);
        //设置标题
        article.put("title", "群发图文接口测试~");
        //阅读原文跳转的URL
        article.put("content_source_url", newsUrl);
        //设置正文内容
        article.put("content", "<h1>标题</h1><p>1234567890</p>!!!");
        //描述部分
        article.put("digest", "窗口描述界面~");
        //将thumb_media_id设置为封面
        article.put("show_cover_pic", "1");

        JSONArray articles = new JSONArray();
        articles.add(article);

        JSONObject object = new JSONObject();
        object.put("articles", articles);

        try {
            //上传图文素材
            String newsResponse = HttpUtils.sendPostBuffers(uploadUrl, object.toJSONString());
            mediaid = net.sf.json.JSONObject.fromObject(newsResponse).getString("media_id");
        } catch (Exception e){

        }

        return  mediaid;
    }
}
