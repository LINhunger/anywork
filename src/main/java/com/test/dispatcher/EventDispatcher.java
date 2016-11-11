package com.test.dispatcher;

import com.test.common.GetUserInfo;
import com.test.enums.Level;
import com.test.message.respModel.Article;
import com.test.message.respModel.NewsMessage;
import com.test.message.respModel.TextMessage;
import com.test.util.Logger;
import com.test.util.MessageUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * 事件消息业务分发器
 * Created by zggdczfr on 2016/10/22.
 */
public class EventDispatcher {

    private static final Logger LOGGER = Logger.getLogger(EventDispatcher.class);

    public static String processEvent(Map<String, String> map){

        String openid = map.get("FromUserName");
        String mpid = map.get("ToUserName");

        /**
         * 对于事务消息的反应，比如发送图片
         */
        NewsMessage newmsg=new NewsMessage();
        newmsg.setToUserName(openid);
        newmsg.setFromUserName(mpid);
        newmsg.setCreateTime(new Date().getTime());
        newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

        //关注事件
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
            System.out.println("====这是关注事件！====");
            try {
                HashMap<String, String> userInfo = GetUserInfo.getUserInfoByOpenID(openid);
                Article article = new Article();
                //图文消息的描述
                article.setDescription("欢迎来到圆锐的微信公众号测试接口~");
                article.setPicUrl(userInfo.get("headimgurl")); //图文消息图片地址
                article.setTitle("尊敬的："+userInfo.get("nickname")+",你好！");  //图文消息标题
                article.setUrl("http://www.baidu.com");  //图文url链接
                List<Article> list=new ArrayList<Article>();
                list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
                newmsg.setArticleCount(list.size());
                newmsg.setArticles(list);
                return MessageUtil.newsMessageToXml(newmsg);

            } catch (URISyntaxException e) {
                System.out.println("关注事件处理发生异常！");
                LOGGER.log(Level.ERROR, "关注事件处理发生异常 : ", e);
            } catch (IOException e) {
                System.out.println("关注事件处理发生异常！");
                LOGGER.log(Level.ERROR, "关注事件处理发生异常 : ", e);
            }
        }

        //取消关注事件
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
            System.out.println("====这是取消关注事件！====");
        }

        //扫描二维码事件
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)){
            System.out.println("====这是扫描二维码事件！====");
        }

        //位置上报事件
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)){
            System.out.println("====这是位置上报事件！====");
        }

        //自定义菜单事件
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)){
            System.out.println("====这是自定义菜单事件！====");
            String eventKey = map.get("EventKey");
            if ("myInformation".equals(eventKey)){
                Article article = new Article();
                article.setDescription("点击打开页面设置个人信息！");
                article.setPicUrl("http://rdveq.free.natapp.cc/WetChat/image/zhang.jpg");
                article.setTitle("设置个人信息~");
                article.setUrl("https://www.baidu.com");
                List<Article> list=new ArrayList<Article>();
                list.add(article);     //这里发送的是单图文
                newmsg.setArticleCount(list.size());
                newmsg.setArticles(list);
                return MessageUtil.newsMessageToXml(newmsg);
            } else {
                //处理其他信息，返回text文本
                TextMessage textmsg = new TextMessage();
                textmsg.setToUserName(openid);
                textmsg.setFromUserName(mpid);
                textmsg.setCreateTime(new Date().getTime());
                textmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                textmsg.setContent("该公众号暂时无法提供该服务！");
                return  MessageUtil.textMessageToXml(textmsg);
            }
        }

        //自定义菜单View事件
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)){
            System.out.println("====这是自定义菜单View事件！====");
        }

        if (map.get("Event").equals("MASSSENDJOBFINISH")){
            System.out.println(map.get("Status"));
        }

        return null;
    }
}
