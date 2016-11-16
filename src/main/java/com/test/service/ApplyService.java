package com.test.service;

import com.test.model.Apply;

import java.util.List;

/**
 * Created by zggdczfr on 2016/11/8.
 */
public interface ApplyService {
    /**
     * 插入申请对象
     * @param apply 申请对象
     * @return 成功为1，失败为0
     */
    public int insertApply(Apply apply);

    /**
     * 处理申请，修改status
     * @param apply 申请对象
     * @return 成功返回1，失败返回0(包括申请不存在，已经处理过的申请返回），若操作有误返回2
     */
    public int updateApply(Apply apply);

    /**
     * 返回该组织的所有未处理申请
     * @param organId 组织id
     * @return 未处理申请集合
     */
    public List<Apply> selectAllByOrganId(int organId);

    /**
     * 根据申请applyId来删除申请
     * @param applyId
     * @return
     */
    int deleteByApplyId(int applyId);
}
