package com.test.service;

import com.test.dao.ApplyDao;
import com.test.dao.HomeworkDao;
import com.test.dao.RelationDao;
import com.test.dao.UserDao;
import com.test.model.Homework;
import com.test.model.Relation;
import com.test.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private HomeworkDao  homeworkDao;



    public User selectUserById(int userId) {
        return userDao.selectOneById(userId);
    }

    public List<Homework> selectHomeworkListByOrganId(Integer organId) {
            return homeworkDao.selectHomeworkListByOrganId(organId);
    }
}
