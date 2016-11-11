package com.test.dao;

import com.test.model.QAnswer;
import com.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by hunger on 2016/11/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class QAnswerDaoTest {
    @Resource
    private QAnswerDao qAnswerDao;
    @Test
    public void insertQAnswer() throws Exception {
        QAnswer qAnswer = new QAnswer();
        User author = new User();
        author.setUserId(1);
        qAnswer.setAuthor(author);
        qAnswer.setQuestId(1);
        qAnswer.setMark("小丫小二郎");
/*        hAnswer.setNote("s);*/
        qAnswerDao.insertQAnswer(qAnswer);
    }

    @Test
    public void updateQAnswer() throws Exception {
        QAnswer qAnswer = new QAnswer();
        User author = new User();
        qAnswer.setqAnswerId(4);
        author.setUserId(1);
        qAnswer.setAuthor(author);
        qAnswer.setQuestId(1);
        qAnswer.setMark("");
        qAnswer.setNote("s");
        qAnswerDao.updateQAnswer(qAnswer);
        System.out.println();
    }

    @Test
    public void selectOneById() throws Exception {
        System.out.println(qAnswerDao.selectOneById(1,2));
    }

    @Test
    public void selectAllByQuestId() throws Exception {
        System.out.println(qAnswerDao.selectAllByQuestId(3));
    }

}