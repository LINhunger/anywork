package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.model.*;
import com.test.service.TimelineService;
import com.test.service.UserService;
import com.test.util.Markdown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by hunger on 2016/11/9.
 */
@Controller
@RequestMapping("/timeline")
public class TimelineController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private TimelineService timelineService;

    /**
     * 显示时间轴
     * @param request 请求对象
     * @param organId 组织id
     * @return dto对象
     */
    @Authority(AuthorityType.NoAuthority)
    @RequestMapping(value = "/{time}/timeline/{organId}",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<Map> showTimeline(HttpServletRequest request,
                                           @PathVariable("time") long time,
                                           @PathVariable("organId") int organId
    ){
        try {
            //将当前组织id写进session
            request.getSession().setAttribute("organId",organId);

            RequestResult<Map> result = timelineService.showTimeline(organId,new Date(time),organId);
            return result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<Map>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 显示作业
     * @param request
     * @return
     */
    @RequestMapping(value = "/homework/{homeworkId}",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<Map> showHomework(HttpServletRequest request,
                                         @PathVariable("homeworkId")int homeworkId) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            RequestResult<Map> result = timelineService.showHomework(homeworkId,user.getUserId());
            return  result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<Map>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 显示公告
     * @param request
     * @param informId
     * @return
     */
    @RequestMapping(value = "/inform/{informId}",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<Inform> showInform(HttpServletRequest request,
                                            @PathVariable("informId")int informId ) {
        try {
            RequestResult<Inform> result = timelineService.showInform(informId);
            return  result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<Inform>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 显示问题
     * @param request
     * @param questId
     * @return
     */
    @RequestMapping(value = "/question/{questId}",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<Map> showQuestion(HttpServletRequest request,
                                           @PathVariable("questId") int questId) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            RequestResult<Map> result = timelineService.showQuestion(questId,user.getUserId());
            return  result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<Map>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 提交作业答案
     * @param request
     * @param hAnswer
     * @return
     */
    @RequestMapping(value = "/hanswer/submit",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?> submitHAnswer(HttpServletRequest request,
                                            @RequestBody HAnswer hAnswer) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            hAnswer.setAuthor(user);
            RequestResult<?> result = timelineService.submitHAnswer(hAnswer);
            return result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<Map>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 提交请求回答
     * @param request
     * @param qAnswer
     * @return
     */
    @RequestMapping(value = "/qanswer/submit",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?>submitQAnswer(HttpServletRequest request,
                                         @RequestBody QAnswer qAnswer) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            qAnswer.setAuthor(user);
            RequestResult<?> result = timelineService.submitQAnswer(qAnswer);
            return result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<Map>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 设置最佳答案
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/qanswer/setting",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?> setBestAnswer(HttpServletRequest request,
                                          @RequestBody Map map) {
        int questId = (Integer) map.get("questId");
        int bestAId = (Integer) map.get("bestAId");
        try {
            User user = (User) request.getSession().getAttribute("user");
            RequestResult<?> result = timelineService.setBestAnswer(questId,bestAId,user.getUserId());
            return  result;
        } catch (Exception e) {
            logger.warn("default exception.\t");
            return new RequestResult<Object>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 评分
     * @param request
     * @param grade
     * @return
     */
    @RequestMapping(value = "/hanswer/score",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?>score(HttpServletRequest request,
                                        @RequestBody Grade grade) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            grade.setUserId(user.getUserId());
            RequestResult<?> result = timelineService.score(grade);
            return  result;
        } catch (Exception e) {
            logger.warn("default exception.\t");
            return new RequestResult<Object>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 发布作业
     * @param request
     * @param homework
     * @return
     */
    @RequestMapping(value = "/homework/release",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?>releaseHomework(HttpServletRequest request,
                                           @RequestBody Homework homework) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            homework.setAuthor(user);
            RequestResult<?> result = timelineService.releaseHomework(homework);
            return  result;
        } catch (Exception e) {
            logger.warn("default exception.\t");
            return new RequestResult<Object>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 发布公告
     * @param request
     * @param inform
     * @return
     */
    @RequestMapping(value = "/inform/release",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?>releaseInform(HttpServletRequest request,
                                         @RequestBody Inform inform) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            inform.setAuthor(user);
            RequestResult<?> result = timelineService.releaseInform(inform);
            return  result;
        } catch (Exception e) {
            logger.warn("default exception.\t");
            return new RequestResult<Object>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 发布请求
     * @param request
     * @param question
     * @return
     */
    @RequestMapping(value = "/question/release",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?> releaseQuestion(HttpServletRequest request,
                                            @RequestBody Question question) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            question.setAuthor(user);
            RequestResult<?> result = timelineService.releaseQuestion(question);
            return  result;
        } catch (Exception e) {
            logger.warn("default exception.\t");
            return new RequestResult<Object>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * Markdown格式的转化和预览
     * @param map markdown格式文本
     * @return
     */
    @RequestMapping(value = "/priview",method = RequestMethod.POST
    )
    @ResponseBody
    public RequestResult<String> priview(@RequestBody Map map) {
        String text = (String) map.get("text");
        try {
            String html = Markdown.pegDown(text);
            RequestResult<String> result = new RequestResult<String>(StatEnum.PREVIEW_SUCCESS,html);
            return  result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return new RequestResult<String>(StatEnum.DEFAULT_WRONG);
        }
    }


}
