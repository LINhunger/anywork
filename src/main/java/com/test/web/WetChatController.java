package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.dao.HomeworkDao;
import com.test.dao.InformDao;
import com.test.dao.QuestionDao;
import com.test.dao.UserDao;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.exception.user.LoginMatchException;
import com.test.exception.user.LoginNotExitUserException;
import com.test.exception.user.ValcodeWrongException;
import com.test.model.*;
import com.test.service.RelationService;
import com.test.service.UserService;
import com.test.util.Markdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.test.common.JSSDK_Config;
import com.test.enums.Level;
import com.test.message.Message;
import com.test.util.Logger;
import com.test.web.util.GlobalConstants;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * 前端用户微信获取
 * Created by zggdczfr on 2016/11/12.
 */
@Controller
@RequestMapping("/wechatconfig")
public class WetChatController {

    private static final Logger LOGGER = Logger.getLogger(WetChatController.class);

    @Resource
    private RelationService relationService;

    @Autowired
    private UserService userService;

    @Resource
    private UserDao userDao;

    @Resource
    private InformDao informDao;

    @Resource
    private HomeworkDao homeworkDao;


    @ResponseBody
    @RequestMapping(value = "jssdk")
    public Message JSSDK_config(@RequestParam(value = "url", required = true)String url){
        try {
            System.out.println("检查前端ajax提交的URL === " + url);
            Map<String, String> configMap = JSSDK_Config.jssdk_Sign(url);
            System.out.println(configMap);

            configMap.put("url", url);
            configMap.put("jsapi_ticket", GlobalConstants.getInterfaceUrl("jsapi_ticket"));
            return Message.success(configMap);

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "JSSDK的ajax请求发生异常 : {0}", e);
            return Message.error();
        }
    }

    /**
     * 微信用户根据微信凭证获取组织列表
     * 注意，不能添加用户权限判断
     * @param openid 微信凭证
     * @return 组织集合
     */
    @RequestMapping("/organs/{openid}")
    @ResponseBody
    public RequestResult<List<Organization>> getOrganByOpenid(@PathVariable String openid){
        List<Organization> organizationList = relationService.selectOrganByOpenid(openid);
        return new RequestResult<List<Organization>>(StatEnum.WEIXIN_ORGANIZATION,
                organizationList);
    }

    /**
     * 用户绑定微信凭证
     * @param map
     * @return
     */
    @RequestMapping("/binding")
    @ResponseBody
    public RequestResult<User> bindingWithWechat(@RequestBody Map map){
        String email = (String)map.get("email");
        String password = (String)map.get("password");
        String openid = (String)map.get("openid");
        try {
            RequestResult<User> login = userService.login(email,password);
            if (121 == login.getState()){
                //验证成功
                userDao.updateOpenidById(((User)login.getData()).getUserId(), openid);

                return new RequestResult<User>(StatEnum.WEIXIN_BIND_SUCCESS, null);
            }
            return new RequestResult<User>(StatEnum.WEIXIN_BIND_FAIL,null);
        } catch (LoginNotExitUserException e) {
            return  new RequestResult<User>(StatEnum.LOGIN_NOT_EXIT_USER,null);
        }catch (LoginMatchException e) {
            return  new RequestResult<User>(StatEnum.LOGIN_USER_MISMATCH,null);
        }catch (Exception e) {
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG,null);
        }
    }

    /**
     * 获取组织的公告或作业内容
     * @param type 公告/作业的标志
     * @param markId 公告/作业id
     * @return
     */
    @RequestMapping("/read/{type}/{markId}")
    @ResponseBody
    public RequestResult<Object> readMarkById(
            @PathVariable int type,
            @PathVariable int markId){
        if (0 == type){
            //若请求是公告
            Inform inform = informDao.selectOneById(markId);
            inform.setMark(Markdown.pegDown(inform.getMark()));
            return new RequestResult<Object>(StatEnum.WEIXIN_INFORM, inform);
        } else if (1 == type){
            //若请求是作业
            Homework homework = homeworkDao.selectOneById(markId);
            homework.setMark(Markdown.pegDown(homework.getMark()));
            return new RequestResult<Object>(StatEnum.WETXIN_QUESTION, homework);
        } else {
            return new RequestResult<Object>(StatEnum.WEIXIN_REQUEST_ERROR);
        }
    }
}
