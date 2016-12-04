package com.test.dao;

import com.test.model.Textpaper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by hunger on 2016/11/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class TextpaperDaoTest {

    @Resource
    private TextpaperDao textpaperDao;
    @Test
    public void insertTextpaper() throws Exception {
            Textpaper textpaper = new Textpaper();
        textpaper.setCreateTime(new Date());
        textpaper.setEndingTime(new Date());
        textpaper.setUserName("aaa");
        textpaper.setTextpaperTitle("aaaa");
        textpaper.setAuthorId(1);
        textpaper.setDifficulty(1);
        textpaper.setTextpaperScore(100);
        textpaper.setTextpaperType(1);
        textpaperDao.insertTextpaper(textpaper);
    }

}
