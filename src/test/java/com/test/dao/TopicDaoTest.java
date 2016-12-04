package com.test.dao;

import com.test.model.Topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by hunger on 2016/11/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class TopicDaoTest {
    @Resource
    private TopicDao topicDao;
    @Test
    public void insertTopic() throws Exception {
        Topic topic = new Topic();
        topic.setA("a");
        topic.setB("b");
        topic.setC("c");
        topic.setD("d");
        topic.setTextpaperId(1);
        topic.setScore(1);
        topic.setType(1);
        topic.setKey("a");
        topicDao.insertTopic(topic);


    }

}