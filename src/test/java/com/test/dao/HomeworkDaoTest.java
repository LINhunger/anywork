package com.test.dao;

import com.test.model.Homework;
import com.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by hunger on 2016/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class HomeworkDaoTest {
    @Resource
    private HomeworkDao homeworkDao;
    @Test
    public void insertHomework() throws Exception {
        Homework homework = new Homework();
        User user = new User();
        user.setUserId(1);
        homework.setAuthor(user);
        homework.setHomeworkTitle("泽胜");
        homework.setEndingTime(new Date());
        homework.setMark("# asdasd");
        homework.setOrganId(1);
        homework.setSubmitCount(1);
        homework.setStatus("466400960@qq.com&");
        System.out.println(homeworkDao.insertHomework(homework));
    }

    @Test
    public void updateHomework() throws Exception {
        Homework homework = new Homework();
        User user = new User();
        user.setUserId(1);
        homework.setHomeworkId(1);
        homework.setAuthor(user);
        homework.setHomeworkTitle("");
        homework.setEndingTime(new Date());
        homework.setOrganId(1);
        homework.setSubmitCount(2);
        homework.setStatus("123");
        System.out.println(homeworkDao.updateHomework(homework));
    }

    @Test
    public void selectOneById() throws Exception {
            System.out.println(homeworkDao.selectOneById(1));
    }

    @Test
    public void selectAllByOrganId() throws Exception {
        System.out.println(homeworkDao.selectAllByOrganId(1,new Date()));
    }

    @Test
    public void deleteHomeworkById() throws Exception {
        System.out.println(homeworkDao.deleteHomeworkById(2));
    }


}