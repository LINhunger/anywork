package com.test.dao;

import com.test.model.Apply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hunger on 2016/11/5.
 */
@Repository
public interface ApplyDao {

    /**
     * 插入申请对象
     * @param apply 申请对象
     * @return 成功为1，失败为0
     */
    int insertApply(@Param("apply") Apply apply);

    /**
     * 处理申请，修改status
     * @param apply 申请对象
     * @return 成功为1，失败为0
     */
    int updateApply(@Param("apply") Apply apply);

    /**
     * 返回该组织的所有未处理申请
     * @param organId 组织id
     * @return 申请集合
     */
    List<Apply> selectAllByOrganId(int organId);

    /**
     * 根据申请applyId来删除申请
     * @param applyId
     * @return
     */
    int deleteByApplyId(int applyId);
}
