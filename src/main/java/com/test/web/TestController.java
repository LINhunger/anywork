package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.model.HAnswer;
import com.test.model.Inform;
import com.test.model.Textpaper;
import com.test.model.User;
import com.test.service.TestService;
import com.test.service.TimelineService;
import com.test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hunger on 2016/11/26.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private TestService testService;
    @Autowired
    private TimelineService timelineService;

    /**
     * 获取作业列表
     * @param request
     * @param organId
     * @return
     */
    @RequestMapping(value = "/list/{organId}",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<List> showTestlist(HttpServletRequest request,
                                            @PathVariable("organId") int organId
    ){
        try {
            RequestResult<List> result = testService.getTextpaperByTitle("");
            return result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<List>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 获取具体的作业
     * @param request
     * @param textpaperId
     * @return
     */
    @RequestMapping(value = "/textpaper/{textpaperId}",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<Textpaper> showTextpaper(HttpServletRequest request,
                                                 @PathVariable("textpaperId") Integer textpaperId
    ){
        try {
            RequestResult<Textpaper> result = testService.getTextpaperById(textpaperId);
            return result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            e.printStackTrace();
            return  new RequestResult<Textpaper>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 发布作业
     * @param request
     * @param textpaper
     * @return
     */
    @RequestMapping(value = "/textpaper/submit",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?> submitTextpaper(HttpServletRequest request,
                                          @RequestBody Textpaper textpaper) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            Integer organId = (Integer) request.getSession().getAttribute("organId");
            textpaper.setUserName(user.getUserName());
            textpaper.setAuthorId(user.getUserId());
            RequestResult<?> result = testService.publishTextpaper(textpaper);
            inform(textpaper, user, organId);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            logger.warn("default exception.\t");
            return  new RequestResult<Object>(StatEnum.DEFAULT_WRONG);
        }
    }


    /**
     * 发布作业
     * @param request
     * @param textpaper
     * @return
     */
    @RequestMapping(value = "/textpaper/update",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?> updateTextpaper(HttpServletRequest request,
                                            @RequestBody Textpaper textpaper) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            Integer organId = (Integer) request.getSession().getAttribute("organId");
            textpaper.setUserName(user.getUserName());
            textpaper.setAuthorId(user.getUserId());
            RequestResult<?> result = testService.updateTextpaper(textpaper);
            inform(textpaper, user, organId);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            logger.warn("default exception.\t");
            return  new RequestResult<Object>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 系统通知方法
     * @param textpaper
     * @param user
     * @param organId
     */
    private void inform(@RequestBody Textpaper textpaper, User user, Integer organId) {
        Inform inform = new Inform();
        inform.setOrganId(organId);
        inform.setInformTitle("系统通知");
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分 E ");
        inform.setMark(textpaper.getName()+":"+textpaper.getTextpaperTitle()+"<br/>" +
                "开始时间："+dateformat.format(textpaper.getCreateTime())+"<br/>" +
                "结束时间："+dateformat.format(textpaper.getEndingTime()));
        inform.setAuthor(user);
        timelineService.releaseInform(inform);
    }

    @RequestMapping(value = "/textpaper/upload",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?> uploadTextpaper(HttpServletRequest request,
                                            @RequestBody Textpaper textpaper) {
        try {
            // TODO: 2016/11/26
            User user = (User) request.getSession().getAttribute("user");
            textpaper.setUserName(user.getUserName());
//            RequestResult<?> result = testService.publishTextpaper(textpaper);
//            return result;
            return  null;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  null;
        }
    }



}
