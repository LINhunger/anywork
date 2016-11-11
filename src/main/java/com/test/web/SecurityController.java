package com.test.web;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.test.dispatcher.EventDispatcher;
import com.test.dispatcher.MsgDispatcher;
import com.test.enums.Level;
import com.test.util.Logger;
import com.test.util.MessageUtil;
import com.test.util.SignUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by zggdczfr on 2016/10/21.
 */
@Controller
@RequestMapping(value = "/wechat")
public class SecurityController {

    private static final long serialVersionUID = 4323197796926899691L;
    private static final Logger LOGGER = Logger.getLogger(SecurityController.class);

    @RequestMapping("test")
    public String test(){
        System.out.println("test");
        return "/index.jsp";
    }

    @RequestMapping(value = "security", method = RequestMethod.GET)
    public void WetChat(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value = "signature", required = true) String signature,
                        @RequestParam(value = "timestamp", required = true) String timestamp,
                        @RequestParam(value = "nonce", required = true) String nonce,
                        @RequestParam(value = "echostr", required = true) String echostr) throws IOException {
        try {
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            }
        } catch (Exception e){
            LOGGER.log(Level.ERROR, "连接微信公众号平台测试失败！");
        }
        System.out.println("连接微信公众号平台测试成功！");
    }

    @RequestMapping(value = "security", method = RequestMethod.POST)
    public void getMessage(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String> map = MessageUtil.parseXml(request);

            System.out.println(map);


            String msgType = map.get("MsgType");
            String respXml;
            if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)){
                //进入事件处理
                respXml = EventDispatcher.processEvent(map);
            } else {
                //进入消息处理
                respXml = MsgDispatcher.processMessage(map);
            }
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(respXml);
            out.close();
        } catch (DocumentException e) {
            LOGGER.log(Level.ERROR, "错误提示：{0}", e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "错误提示：{0}", e.getMessage());
        }
    }
}
