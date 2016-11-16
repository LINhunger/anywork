package com.test.dispatcher;

import com.test.message.respModel.Article;
import com.test.message.respModel.NewsMessage;
import com.test.message.respModel.TextMessage;
import com.test.util.MessageUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息业务处理分发器
 * Created by zggdczfr on 2016/11/7.
 */
public class MsgDispatcher {

    public static String processMessage(Map<String, String>map){

        String openid = map.get("FromUserName"); //用户openid
        String mpid = map.get("ToUserName"); //公众号原始ID


        //文本消息
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
            //普通文本消息
            TextMessage textMsg = new TextMessage();
            textMsg.setToUserName(openid);
            textMsg.setFromUserName(mpid);
            textMsg.setCreateTime(new Date().getTime());
            textMsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            //设置文本消息内容
            textMsg.setContent("当前用户的openid = "+ openid);
            return MessageUtil.textMessageToXml(textMsg);
        }

        //图片消息
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
            System.out.println("====图片消息====");
        }

        //链接消息
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
            System.out.println("====链接消息====");
        }

        //位置消息
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
            System.out.println("====位置消息====");
        }

        //视频消息
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)){
            System.out.println("====视频消息====");
        }

        //语音消息
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
            System.out.println("====语音消息====");
        }

        return null;
    }
}
