package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
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
 * 与微信端服务器对接
 * Created by zggdczfr on 2016/11/8.
 */
@Controller
@Authority(AuthorityType.NoValidate)
@RequestMapping(value = "/wechat")
public class SecurityController {

    private static final long serialVersionUID = 4323197796926899691L;
    private static final Logger LOGGER = Logger.getLogger(SecurityController.class);

    @RequestMapping("test")
    public String test(){
        System.out.println("test");
        return "/index";
    }

    /**
     * 与微信服务器进行身份对接验证
     * @param request
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @throws IOException
     */
    @RequestMapping(value = "security", method = RequestMethod.GET)
    public void WetChat(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value = "signature", required = true) String signature,
                        @RequestParam(value = "timestamp", required = true) String timestamp,
                        @RequestParam(value = "nonce", required = true) String nonce,
                        @RequestParam(value = "echostr", required = true) String echostr) throws IOException {
        try {
            System.out.println("微信链接");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                System.out.println(echostr);
                out.close();
            }
        } catch (Exception e){
            LOGGER.log(Level.DEBUG, "连接微信公众号平台测试失败！");
            LOGGER.log(Level.ERROR, "错误提示：{0}", e.getMessage());
        }
        LOGGER.log(Level.DEBUG, "连接微信公众号平台测试成功！");
    }

    /**
     * 与微信端服务器进行信息交互
     * @param request
     * @param response
     */
    @RequestMapping(value = "security", method = RequestMethod.POST)
    public void getMessage(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String> map = MessageUtil.parseXml(request);

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
            LOGGER.log(Level.ERROR, "微信服务器交互，错误提示：{0}", e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "微信服务器交互，错误提示：{0}", e.getMessage());
        }
    }
}
