package com.test.service;

import com.test.dao.ApplyDao;
import com.test.dao.RelationDao;
import com.test.dao.UserDao;
import com.test.model.Relation;
import com.test.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hunger on 2016/11/16.
 */
@Service
public class AdminService {

    @Resource
    private ApplyDao applyDao;

    @Resource
    private RelationDao relationDao;
    @Resource
    private UserDao userDao;



    public User selectUserById(int userId) {
        return userDao.selectOneById(userId);
    }

}
