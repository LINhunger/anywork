package com.test.dao;

import com.test.model.QAnswer;
import com.test.model.Question;
import com.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by hunger on 2016/11/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class QuestionDaoTest {
    @Resource
    private QuestionDao questionDao;
    @Test
    public void insertQuestion() throws Exception {
        Question question  = new Question();
        User author = new User();
        author.setUserId(1);
        QAnswer qAnswer = new QAnswer();
        qAnswer.setqAnswerId(1);
        question.setOrganId(1);
        question.setQuestTitle("德玛西亚");
        question.setMark("## 123");
        question.setqAnswer(qAnswer);
        question.setAuthor(author);
        questionDao.insertQuestion(question);
    }

    @Test
    public void updateQuestion() throws Exception {
        Question question  = new Question();
        question.setQuestId(1);
        User author = new User();
        author.setUserId(12);
        QAnswer qAnswer = new QAnswer();
        qAnswer.setqAnswerId(1);
        question.setOrganId(1);
        question.setQuestTitle("开车了，快上车");
        question.setMark("");
        question.setqAnswer(qAnswer);
        question.setAuthor(author);
        questionDao.updateQuestion(question);
    }

    @Test
    public void selectOneById() throws Exception {
        System.out.println(questionDao.selectOneById(1));
       String test2 ="Question{questId=1, name='null', organId=1, questTitle='开车了，快上车', mark='## 123', author=User{userId=1, userName='vi2', email='466400960@qq.com', password='null', phone='null', school='null', isWeChat=0, openid='null', createTime=null, organs=null}, createTime=Sun Nov 06 16:27:21 CST 2016, qAnswer=QAnswer{qAnswerId=1, questId=3, author=User{userId=1, userName='vi2', email='466400960@qq.com', password='null', phone='null', school='null', isWeChat=0, openid='null', createTime=null, organs=null}, mark='2222', note='null', createTime=Sun Nov 06 19:04:44 CST 2016}}";
        String test ="Question{questId=1, name='null', organId=1, questTitle='开车了，快上车', mark='## 123', author=User{userId=1, userName='vi2', email='466400960@qq.com', password='null', phone='null', school='null', isWeChat=0, openid='null', createTime=null, organs=null}, createTime=Sun Nov 06 16:27:21 CST 2016, qAnswer=QAnswer{qAnswerId=1, questId=3, author=User{userId=1, userName='vi2', email='466400960@qq.com', password='null', phone='null', school='null', isWeChat=0, openid='null', createTime=null, organs=null}, mark='2222', note='null', createTime=Sun Nov 06 19:04:44 CST 2016}}";
        System.out.println(test.equalsIgnoreCase(test2));
    }

    @Test
    public void seclectAllByOrganId() throws Exception {
            System.out.println(questionDao.seclectAllByOrganId(1,new Date()));
    }

    @Test
    public void deleteQuestionById() throws Exception {
            questionDao.deleteQuestionById(3);
    }

    @Test
    public void setBestAId() throws Exception {
        questionDao.setBestAId(1,0);
    }

}