package com.test.dispatcher;

import com.test.common.GetUserInfo;
import com.test.enums.Level;
import com.test.message.respModel.Article;
import com.test.message.respModel.NewsMessage;
import com.test.message.respModel.TextMessage;
import com.test.util.Logger;
import com.test.util.MessageUtil;
import com.test.web.util.GlobalConstants;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * 事件消息业务分发器
 * Created by zggdczfr on 2016/11/7.
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
            LOGGER.log(Level.DEBUG, openid + " 关注公众号");
            try {
                HashMap<String, String> userInfo = GetUserInfo.getUserInfoByOpenID(openid);
                Article article = new Article();
                //图文消息的描述
                article.setDescription("欢迎来到 anywork 微信公众号~");
                article.setPicUrl(userInfo.get("headimgurl")); //图文消息图片地址,获取用户头像
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
            Article article = new Article();

            if ("myInformation".equals(eventKey)){
                //获取修改个人信息图文
                article.setDescription("点击打开页面设置个人信息！");
                article.setPicUrl(GlobalConstants.interfaceUrlProperties.get("servlet_url")+"image/user.jpg");
                article.setTitle("设置个人信息~");
                article.setUrl(GlobalConstants.interfaceUrlProperties.get("servlet_url")
                        +"src/html/wechat/bind.html?openid="+openid);
                List<Article> list=new ArrayList<Article>();
                list.add(article);     //这里发送的是单图文
                newmsg.setArticleCount(list.size());
                newmsg.setArticles(list);
                return MessageUtil.newsMessageToXml(newmsg);

            } else if ("myOrgan".equals(eventKey)){
                //获取我的组织图文
                article.setDescription("点击查看我已经加入的组织~");
                article.setPicUrl(GlobalConstants.interfaceUrlProperties.get("servlet_url")+"image/organ.jpg");
                article.setTitle("查看我的组织");
                article.setUrl(GlobalConstants.interfaceUrlProperties.get("servlet_url")
                        +"src/html/wechat/search.html?openid="+openid);
                List<Article> list=new ArrayList<Article>();
                list.add(article);     //这里发送的是单图文
                newmsg.setArticleCount(list.size());
                newmsg.setArticles(list);
                return MessageUtil.newsMessageToXml(newmsg);

            }  else {
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
