package com.test.dao;

import com.test.model.Organization;
import com.test.model.Relation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hunger on 2016/11/5.
 */
public interface RelationDao {

    /**
     * 新增组织用户，联表修改organization的organCount
     * @param relation
     * @return 成功为1，失败为0
     */
    int insertRelation(@Param("relation") Relation relation);

    /**
     * 删除组织用户，联表修改organization的organCount
     * role为3时才可以删除；负责人为1、管理员为2、成员为3
     * @param relationId
     * @return 成功为1，失败为0
     */
    int deleteRelation(@Param("relationId") int relationId);

    /**
     * 根据relationId查找relation
     * @param relationId
     * @return Relation对象
     */
    Relation selectOneById(@Param("relationId")int relationId);

    /**
     * 根据组织id来查找用户
     * @param organId
     * @return Relation集合
     */
    List<Relation > selectAllByOrganId(@Param("organId") int organId);

    /**
     * 根据组织id来查找微信用户凭证
     * @param organId
     * @return String集合
     */
    List<String> selectOpenidByOrganId(@Param("organId") int organId);

    /**
     * 根据用户id来查找组织对象列表
     * @param userId 用户id
     * @return 组织对象集合
     */
    List<Organization> selectOrganByUserId(@Param("userId") int userId);

    /**
     * 修改用户组织关系信息
     * @param relation 关系对象
     * @return 成功为1，失败为0
     */
    int updateRoleByRelation(@Param("relation") Relation relation);
}
