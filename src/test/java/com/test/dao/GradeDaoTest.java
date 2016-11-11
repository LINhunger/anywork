package com.test.dao;

import com.test.model.Grade;
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
public class GradeDaoTest {
    @Resource
    private GradeDao gradeDao;
    @Test
    public void insertGrade() throws Exception {
            Grade grade = new Grade();
        grade.setHomeworkId(100);
        grade.setAnswerId(200);
        grade.setUserId(2);
        grade.setGrade(5);
        gradeDao.insertGrade(grade);
    }

    @Test
    public void selectOneById() throws Exception {
            System.out.println(gradeDao.selectOneById(1,2));
    }

}