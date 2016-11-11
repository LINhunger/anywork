package com.test.web;

import com.test.dto.RequestResult;
import com.test.service.TimelineService;
import com.test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
    @RequestMapping(value = "/{time}/timeline/{organId}",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<Set> showTimeline(HttpServletRequest request,
                                           @PathVariable("time") Date time,
                                           @PathVariable("organId") int organId
    ){
        //将当前组织id写进session
        request.getSession().setAttribute("organId",organId);
        return null;
    }


}
