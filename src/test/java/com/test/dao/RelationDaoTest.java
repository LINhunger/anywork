package com.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hunger on 2016/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RelationDaoTest {
    @Autowired
    private RelationDao relationDao;
    @Test
    public void selectRoleByRelation() throws Exception {

//            System.out.println(relationDao.selectRoleByRelation(2,1));

        List<String> openidList = relationDao.selectOpenidByOrganId(1);
        //将组织openid中的null去掉
        openidList.removeAll(Collections.singleton(null));
        System.out.println(openidList);
    }

}