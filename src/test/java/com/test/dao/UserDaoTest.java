package com.test.dao;

import com.test.model.Apply;
import com.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by hunger on 2016/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class UserDaoTest {
    @Resource
    private ApplyDao applyDao;
    @Resource
    private UserDao userDao;
    @Test
    public void insertUser() throws Exception {
        for(int i = 5087;i<=5126;i++){
            Apply apply = new Apply();
            apply.setOrganId(36);
            User user = new User();
            user.setUserId(i);
            apply.setSender(user);
            applyDao.insertApply(apply);
        }

    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setUserId(6);
/*        user.setUserName("vi2");
        user.setPassword("");
        user.setEmail("466400960@qq.com");
        user.setIsWeChat(3);*/
        user.setPicture(user.getUserId()+".jpg");
        System.out.println(userDao.updateUser(user));

    }

    @Test
    public void selectOneById() throws Exception {
        System.out.println(userDao.selectOneById(1));

    }

    @Test
    public void selectOneByEmail() throws Exception {
        System.out.println(userDao.selectOneByEmail("123456789@qq.com"));
    }

    @Test
    public void updateOpenidById() throws Exception {
        System.out.println(userDao.updateOpenidById(1,"wo shi ni ba ba"));
    }

}