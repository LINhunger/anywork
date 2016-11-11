package com.test.dao;

import com.test.model.HAnswer;
import com.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by hunger on 2016/11/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class HAnswerDaoTest {
    @Resource
    private HAnswerDao hAnswerDao;
    @Test
    public void insertHAnswer() throws Exception {
        HAnswer hAnswer = new HAnswer();
        User author = new User();
        author.setUserId(1);
        hAnswer.setAuthor(author);
        hAnswer.setHomeworkId(1);
        hAnswer.setMark("小丫小二郎");
        hAnswer.setGrade5(100);
/*        hAnswer.setNote("s);*/
/*
        Map temp=new HashMap();
        temp.put("homeworkId",hAnswer.getHomeworkId());
        temp.put("userId",hAnswer.getAuthor().getUserId());
        temp.put("mark",hAnswer.getMark());
        temp.put("note",hAnswer.getNote());
*/

        hAnswerDao.insertHAnswer(hAnswer);

    }

    @Test
    public void updateHAnswer() throws Exception {
        HAnswer hAnswer = new HAnswer();
        User author = new User();
        author.setUserId(1);
        hAnswer.sethAnswerId(1);
        hAnswer.setAuthor(author);
        hAnswer.setHomeworkId(1);
        hAnswer.setMark("郎");
        hAnswer.setGrade5(100);
        hAnswer.setNote("sd");
        hAnswerDao.updateHAnswer(hAnswer);
    }

    @Test
    public void selectOneById() throws Exception {
        System.out.println(hAnswerDao.selectOneById(1,1));
        System.out.println(hAnswerDao.selectAllByHomeworkId(1));
    }


}