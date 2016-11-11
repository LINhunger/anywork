package com.test.dao;

import com.test.model.Inform;
import com.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by hunger on 2016/11/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class InformDaoTest {
    @Resource
    private InformDao informDao;
    @Test
    public void insertInform() throws Exception {
            Inform inform = new Inform();
            User author = new User();
            author.setUserId(1);
            inform.setOrganId(1);
            inform.setAuthor(author);
            inform.setMark("66666");
            inform.setInformTitle("1234567");
            informDao.insertInform(inform);
    }

    @Test
    public void updateInform() throws Exception {
        Inform inform = new Inform();
        User author = new User();
        author.setUserId(1);
        inform.setInformId(1);
        inform.setOrganId(1);
        inform.setAuthor(author);
        inform.setMark("");
        inform.setInformTitle("1234567");
        informDao.updateInform(inform);
    }

    @Test
    public void selectOneById() throws Exception {
        System.out.println(informDao.selectOneById(2));
    }

    @Test
    public void selectAllByOrganId() throws Exception {
        System.out.println(informDao.selectAllByOrganId(1,new Date()));
    }

    @Test
    public void deleteInformById() throws Exception {
        System.out.println(informDao.deleteInformById(3));
    }

}