package com.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by hunger on 2016/11/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class TimelineServiceTest {

    @Resource
    private TimelineService timelineService;
    @Test
    public void showTimeline() throws Exception {
    System.out.println(timelineService.showTimeline(1,new Date(new Date().getTime()-86400000*3),1));
    }

    @Test
    public void showHomework() throws Exception {

    }

    @Test
    public void showInform() throws Exception {

    }

    @Test
    public void showQuestion() throws Exception {

    }

    @Test
    public void releaseHomework() throws Exception {

    }

    @Test
    public void releaseInform() throws Exception {

    }

    @Test
    public void releaseQuestion() throws Exception {

    }

    @Test
    public void submitHAnswer() throws Exception {

    }

    @Test
    public void submitQAnswer() throws Exception {

    }

    @Test
    public void setBestAnswer() throws Exception {

    }

    @Test
    public void score() throws Exception {

    }

}