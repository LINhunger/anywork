package com.test.service.impl;

import com.test.dao.ApplyDao;
import com.test.model.Apply;
import com.test.service.ApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zggdczfr on 2016/11/8.
 */
@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource
    private ApplyDao applyDao;

    /**
     * 插入申请对象
     * @param apply 申请对象
     * @return 成功为1，失败为0
     */
    public int insertApply(Apply apply){
        return applyDao.insertApply(apply);
    }

    /**
     * 处理申请，修改status
     * @param apply 申请对象
     * @return 成功返回1，失败返回0(包括申请不存在，已经处理过的申请返回），若操作有误返回2
     */
    public int updateApply(Apply apply){
        //更新时不能将申请设置为未处理状态
        if(0 == apply.getStatus())
            return 2;
        return applyDao.updateApply(apply);
    }

    public List<Apply> selectAllByOrganId(int organId) {
        List<Apply> applyList = applyDao.selectAllByOrganId(organId);
        return applyList;
    }

    public int deleteByApplyId(int applyId) {
        return applyDao.deleteByApplyId(applyId);
    }
}
